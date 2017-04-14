package com.github.appreciated.quickstart.material.design;


import com.github.appreciated.quickstart.base.authentication.Util;
import com.github.appreciated.quickstart.base.authentication.login.AccessControl;
import com.github.appreciated.quickstart.base.components.DownloadButton;
import com.github.appreciated.quickstart.base.components.UploadButton;
import com.github.appreciated.quickstart.base.navigation.RegistrationControl;
import com.github.appreciated.quickstart.base.navigation.WebAppDescription;
import com.github.appreciated.quickstart.base.navigation.actions.*;
import com.github.appreciated.quickstart.base.navigation.container.SubPageNavigator;
import com.github.appreciated.quickstart.base.navigation.interfaces.HasContextActions;
import com.github.appreciated.quickstart.base.navigation.interfaces.HasSearch;
import com.github.appreciated.quickstart.base.navigation.interfaces.NavigationDesignInterface;
import com.github.appreciated.quickstart.base.navigation.interfaces.Subpage;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Created by appreciated on 10.12.2016.
 */
public class MaterialMobileView extends MobileNavigationDesign implements NavigationDesignInterface {

    public MaterialMobileView() {
        logout.addClickListener(event -> {
            Util.invalidateSession();
        });
        closeSearch.addClickListener(e -> {
            searchbarWrapper.setVisible(false);
            searchbar.clear();
        });
    }

    @Override
    public void initWithTitle(String title) {
        menuButton.setCaption(title);
    }

    @Override
    public void initNavigationElements(Stream<Subpage> pages) {
        navigationElements.removeAllComponents();
        pages.forEach(element -> {
            if (element instanceof SubPageNavigator) {
                addMenuElement(element, true);
                ((SubPageNavigator) element).getPagingElements().getSubpages().forEach(subpage -> {
                    addChildElement((SubPageNavigator) element, subpage, false);
                });
            } else {
                addMenuElement(element, false);
            }
        });
    }

    private void addChildElement(SubPageNavigator root, Subpage subpage, boolean hasChildren) {
        // Wrapper for the Java script part at the attach() method to not override the vaadin on click events
        HorizontalLayout wrapper = new HorizontalLayout();
        wrapper.setHeight(50, Unit.PIXELS);
        wrapper.setWidth(100, Unit.PERCENTAGE);
        if (hasChildren) {
            wrapper.addStyleName("mobile-tab-wrapper-root");
        } else {
            wrapper.addStyleName("mobile-tab-wrapper");
        }
        Button button = new Button(subpage.getNavigationName());
        button.addStyleName("mobile-tab");
        button.setSizeFull();
        wrapper.addComponent(button);
        button.addClickListener(clickEvent -> root.navigateTo());
        root.setCurrentSubpage(subpage);
        navigationElements.addComponent(wrapper);
    }

    private void addMenuElement(Subpage subpage, boolean hasChildren) {
        // Wrapper for the Java script part at the attach() method to not override the vaadin on click events
        HorizontalLayout wrapper = new HorizontalLayout();
        wrapper.setHeight(50, Unit.PIXELS);
        wrapper.setWidth(100, Unit.PERCENTAGE);
        if (hasChildren) {
            wrapper.addStyleName("mobile-tab-wrapper-root");
        } else {
            wrapper.addStyleName("mobile-tab-wrapper");
        }
        Button button = new Button(subpage.getNavigationName());
        button.addStyleName("mobile-tab");
        button.setSizeFull();
        wrapper.addComponent(button);
        button.addClickListener(clickEvent -> subpage.navigateTo());
        navigationElements.addComponent(wrapper);
    }

    @Override
    public void initUserFunctionality(WebAppDescription description) {

    }

    @Override
    public void initCustomMenuElements(List<Component> components) {
        menuElements.removeAllComponents();
        components.forEach(component -> menuElements.addComponent(component));
    }

    @Override
    public void allowPercentagePageHeight(boolean allow) {
        if (allow) {
            this.componentHolder.setHeight(100, Unit.PERCENTAGE);
        } else {
            this.componentHolder.setHeightUndefined();
        }
    }

    @Override
    public void attach() {
        super.attach();

        /**
         * Some additional Javascript to dodge the server-round trip time on mobile devices for the opening and closing
         * animation
         *
         * Be careful when editing this the domIds are set via the Vaadin designer.
         */
        /**
         * A little bit of JavaScript "hacking" because we don't want to wait for the for the whole server-round-trip-time
         * on mobile devices to toggle the animation this might actually take pretty long also we are dodging the Widgetset
         * compilation. But it has also its disadvantages.
         */

        String menuButtonId = "menu-button";
        String menuId = "menu-wrapper";

        /**
         * Open / Close the menu when clicking on Menubutton
         */
        com.vaadin.server.Page.getCurrent().getJavaScript().execute(
                "document.getElementById('" + menuButtonId + "').onclick = function(){" +
                        "\n" + "document.getElementById('" + menuId + "').classList.toggle('menu-show');" +
                        "\n" + "};");

        /**
         * Close The menu when clicking on a Subpages Button
         */
        com.vaadin.server.Page.getCurrent().getJavaScript().execute(
                "var elements = document.getElementsByClassName('mobile-tab-wrapper'); \n" +
                        "for (var i = 0; i < elements.length; i++) {\n" +
                        "   elements[i].addEventListener(\"click\", function() {\n" +
                        "       document.getElementById('" + menuId + "').classList.toggle('menu-show');\n" +
                        "   });\n" +
                        "}");

        /**
         * Close/open the menu when swiping left/right
         */

        com.vaadin.server.Page.getCurrent().getJavaScript().execute("var xDown = null;\n" +
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
    public AbstractOrderedLayout getHolder() {
        return componentHolder;
    }

    @Override
    public void disableLogout() {
        logoutWrapper.setVisible(false);
    }

    @Override
    public void setCurrentContainerLabel(String label) {
        menuButton.setCaption(label);
    }

    @Override
    public void setCurrentActions(HasContextActions contextNavigable) {
        if (contextNavigable == null) {
            contextButtonContainer.setVisible(false);
        } else {
            contextButtonWrapper.forEach(component -> {
                if (component != floatingButton) {
                    contextButtonWrapper.removeComponent(component);
                }
            });
            contextButtonContainer.setVisible(true);
            smallContextButtonContainer.removeAllComponents();
            List<Action> actions = contextNavigable.getContextActions();
            Map<Action, Component> generatedButtons = new HashMap<>();
            floatingButton.setIcon(VaadinIcons.ELLIPSIS_V);
            actions.stream().forEach(action -> {
                Component buttonComponent = null;
                if (action instanceof CustomAction) {
                    CustomAction customAction = (CustomAction) action;
                    if (customAction.isMobileCompliant()) {
                        buttonComponent = customAction.getMobileComponent();
                    }
                } else if (action instanceof DownloadAction) {
                    buttonComponent = new DownloadButton((DownloadAction) action);
                } else if (action instanceof UploadAction) {
                    buttonComponent = new HorizontalLayout();
                    ((HorizontalLayout) buttonComponent).addComponent(new UploadButton((UploadAction) action));
                } else if (action instanceof ClickAction) {
                    buttonComponent = new Button(action.getResource());
                    ((Button) buttonComponent).addClickListener(clickEvent -> ((ClickAction) action).getListener().actionPerformed(null));
                }
                if (buttonComponent != null) {
                    generatedButtons.put(action, buttonComponent);
                }
            });
            if (generatedButtons.size() > 1) {
                floatingButton.setVisible(true);
                generatedButtons.forEach((action, component) -> {
                    component.setStyleName("context-button");
                    component.setWidth("50px");
                    component.setHeight("50px");
                    smallContextButtonContainer.addComponent(component);
                });
                setStyle(contextButtonContainer, "display-none", false);
                Button.ClickListener clickListener = (Button.ClickListener) clickEvent -> {
                    toggleStyle(smallContextButtonContainer, "display-none");
                };
                floatingButton.addClickListener(clickListener);
            } else {
                floatingButton.setVisible(false);
                generatedButtons.forEach((action, component) -> {
                    component.setStyleName("context-button");
                    component.setWidth("60px");
                    component.setHeight("60px");
                    contextButtonWrapper.addComponent(component);
                });
            }
        }
    }

    @Override
    public void setCurrentSearchNavigable(HasSearch navigable) {
        if (navigable == null) {
            showSearchbarButton.addStyleName("hidden");
            searchbarWrapper.setVisible(false);
        } else {
            showSearchbarButton.removeStyleName("hidden");
            showSearchbarButton.addClickListener((Button.ClickListener) clickEvent -> {
                searchbarWrapper.setVisible(true);
                searchbar.addValueChangeListener(valueChangeEvent -> navigable.valueChange(valueChangeEvent));
                searchbar.focus();
                searchbar.addBlurListener(blurEvent -> searchbarWrapper.setVisible(false));
            });
        }
    }

    @Override
    public void initWithAccessControl(AccessControl accessControl) {

    }

    @Override
    public void initRegistrationControl(RegistrationControl registrationControl) {

    }

    public Button getMenuButton() {
        return menuButton;
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

    public HorizontalLayout getLogoutWrapper() {
        return logoutWrapper;
    }

    public Button getLogout() {
        return logout;
    }

    public static void toggleStyle(Component component, String style) {
        if (component.getStyleName().contains(style)) {
            component.removeStyleName(style);
        } else {
            component.addStyleName(style);
        }
    }

    public static void setStyle(Component component, String style, boolean enabled) {
        if (!enabled) {
            component.removeStyleName(style);
        } else {
            component.addStyleName(style);
        }
    }

}
