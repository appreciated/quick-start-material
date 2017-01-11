package com.github.appreciated.quickstart.material;


import com.github.appreciated.quickstart.base.navigation.Navigation;
import com.github.appreciated.quickstart.base.interfaces.NavigatorInterface;
import com.github.appreciated.quickstart.base.vaadin.Util;
import com.vaadin.server.Page;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

/**
 * Created by appreciated on 10.12.2016.
 */
public abstract class MobileNavigationView extends MobileNavigationDesign implements NavigatorInterface {

    private final Navigation navigation;

    public MobileNavigationView() {
        menuButton.setCaption(getConfiguration().getTitle());
        navigation = new Navigation(this, floatingButton, componentHolder, contextButtonContainer, smallContextButtonContainer);
        menuButtons.removeAllComponents();
        getConfiguration().getButtons().stream().forEach(entry -> {
            /**
             * Wrapper for the Java script part at the attach() method to not override the vaadin on click events
             */
            HorizontalLayout wrapper = new HorizontalLayout();
            wrapper.setHeight(50, Unit.PIXELS);
            wrapper.setWidth(100, Unit.PERCENTAGE);
            wrapper.addStyleName("mobile-tab-wrapper");
            Button button = entry.getKey();
            button.addStyleName("mobile-tab");
            button.setSizeFull();
            wrapper.addComponent(button);
            menuButtons.addComponent(wrapper);
            navigation.addNavigation(button, entry.getValue());
        });

        logout.addClickListener(event -> {
            Util.invalidateSession();
        });
        navigation.navigateTo(getConfiguration().getStartNavigable());
    }


    @Override
    public void attach() {
        super.attach();

        /**
         * Some additional Javascript to dodge the server-round trip time on mobile devices for the openning and closing
         * animation
         *
         * Be careful when editing this the domIds are set via the Vaadin designer.
         */
        /**
         * A little bit of JavaScript "hacking" because we don't want to wait for the for the whole server-round-trip-time
         * on mobile devices to toogle the animation this might actually take pretty long also we are dodging the Widgetset
         * compilation. But ofcourse is has also its disadvantages.
         */


        String menButtonId = "menu-button";
        String menuId = "menu-wrapper";

        /**
         * Open / Close the menu when clicking on Menubutton
         */
        Page.getCurrent().getJavaScript().execute(
                "document.getElementById('" + menButtonId + "').onclick = function(){" +
                        "\n" + "document.getElementById('" + menuId + "').classList.toggle('menu-show');" +
                        "\n" + "};");

        /**
         * Close The menu when clicking on a Navigation Button
         */
        Page.getCurrent().getJavaScript().execute(
                "var elements = document.getElementsByClassName('mobile-tab-wrapper'); \n" +
                        "for (var i = 0; i < elements.length; i++) {\n" +
                        "   elements[i].addEventListener(\"click\", function() {\n" +
                        "       document.getElementById('" + menuId + "').classList.toggle('menu-show');\n" +
                        "   });\n" +
                        "}");

        /**
         * Close/open the menu when swiping left/right
         */

        Page.getCurrent().getJavaScript().execute("var xDown = null;\n" +
                "var yDown = null;\n" +
                "\n" +
                "function handleTouchStart(evt) {\n" +
                "    xDown = evt.touches[0].clientX;\n" +
                "    yDown = evt.touches[0].clientY;\n" +
                "}\n" +
                "\n" +
                "function handleTouchMove(evt) {\n" +
                "    if ( ! xDown || ! yDown ) {\n" +
                "        return;\n" +
                "    }\n" +
                "\n" +
                "    var xUp = evt.touches[0].clientX;\n" +
                "    var yUp = evt.touches[0].clientY;\n" +
                "    var xDiff = xDown - xUp;\n" +
                "    var yDiff = yDown - yUp;\n" +
                "\n" +
                "    if ( Math.abs( xDiff ) > Math.abs( yDiff ) ) {/*most significant*/\n" +
                "           console.log(Math.abs( xDiff ));      \n" +
                "        if ( xDiff > 0 && Math.abs( xDiff ) >= 10) {\n" +
                            // left swipe
                "           document.getElementById('" + menuId + "').classList.remove('menu-show');\n" +
                "        } else if (Math.abs( xDiff ) >= 10) {\n" +
                            // right swipe
                "           document.getElementById('" + menuId + "').classList.add('menu-show');\n" +
                "        }\n" +
                "    } else {\n" +
                "        if ( yDiff > 0 ) {\n" +
                "           // console.log(\"up swipe\"); \n" +
                "        } else { \n" +
                "           // console.log(\"down swipe\"); \n" +
                "        }\n" +
                "    }\n" +
                "    /* reset values */\n" +
                "    xDown = null;\n" +
                "    yDown = null;\n" +
                "}\n" +
                "\n" +
                "document.addEventListener(\"touchstart\", handleTouchStart, false);\n" +
                "document.addEventListener(\"touchmove\", handleTouchMove, false);"
        );
    }

    @Override
    public Navigation getNavigation() {
        return navigation;
    }

    @Override
    public void disableLogout() {
        logoutWrapper.setVisible(false);
    }

    public Button getMenuButton() {
        return menuButton;
    }

    public HorizontalLayout getComponentHolder() {
        return componentHolder;
    }

    public VerticalLayout getContextButtonContainer() {
        return contextButtonContainer;
    }

    public VerticalLayout getSmallContextButtonContainer() {
        return smallContextButtonContainer;
    }

    public Button getFloatingButton() {
        return floatingButton;
    }

    public HorizontalLayout getMenuWrapper() {
        return menuWrapper;
    }

    public VerticalLayout getMenu() {
        return menu;
    }

    public VerticalLayout getMenuButtons() {
        return menuButtons;
    }

    public HorizontalLayout getLogoutWrapper() {
        return logoutWrapper;
    }

    public Button getLogout() {
        return logout;
    }
}
