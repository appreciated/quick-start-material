package com.github.appreciated.quickstart.material;


import com.github.appreciated.quickstart.base.navigation.Navigation;
import com.github.appreciated.quickstart.base.interfaces.NavigatorInterface;
import com.github.appreciated.quickstart.base.vaadin.Util;
import com.vaadin.ui.*;

/**
 * Created by appreciated on 04.12.2016.
 */
public abstract class DesktopNavigationView extends DesktopNavigationDesign implements NavigatorInterface {
    public static final String CONFIGURATION_FULLHEIGHT_NAVIGATIONBAR = "full_height_navigationbar";
    public static final String CONFIGURATION_HIDE_ICON = "hide_icon";
    public static final String CONFIGURATION_HIDE_TITLE = "hide_title";
    private Navigation navigation = null;

    public DesktopNavigationView() {
        navigation = new Navigation(this, floatingButton, componentHolder, contextButtonContainer, smallContextButtonContainer);
        title.setValue(getConfiguration().getTitle());
        buttonContainer.removeAllComponents();
        getConfiguration().getButtons().stream().forEach(entry -> {
            Button button = entry.getKey();
            button.addStyleName("tab");
            button.setHeight(60, Unit.PIXELS);
            buttonContainer.addComponent(button);
            navigation.addNavigation(button, entry.getValue());
        });
        logout.addClickListener(event -> {
            Util.invalidateSession();
        });
        navigation.navigateTo(getConfiguration().getStartNavigable());

        getConfiguration().getConfiguration().stream().forEach(entry -> {
            if (entry.getValue().booleanValue() == true) {
                switch (entry.getKey()) {
                    case CONFIGURATION_FULLHEIGHT_NAVIGATIONBAR:
                        menuButtonContainer.setWidth(100, Unit.PERCENTAGE);
                        break;
                    case CONFIGURATION_HIDE_ICON:
                        iconContainer.setVisible(false);
                        break;
                    case CONFIGURATION_HIDE_TITLE:
                        title.setVisible(false);
                        break;
                }
            }
        });
    }

    @Override
    public Navigation getNavigation() {
        return navigation;
    }

    @Override
    public void disableLogout() {
        logout.setVisible(false);
    }

    /*
     * To make the view mosre customizable allow every Property to be accessed
     */

    public HorizontalLayout getIconContainer() {
        return iconContainer;
    }

    public Label getTitleLabel() {
        return title;
    }

    public HorizontalLayout getMenuButtonContainer() {
        return menuButtonContainer;
    }

    public CssLayout getButtonContainer() {
        return buttonContainer;
    }

    public Button getHome() {
        return home;
    }

    public Button getAbout() {
        return about;
    }

    public Button getLogout() {
        return logout;
    }

    public VerticalLayout getContextButtonContainer() {
        return contextButtonContainer;
    }

    public Button getFloatingButton() {
        return floatingButton;
    }

    public VerticalLayout getSmallContextButtonContainer() {
        return smallContextButtonContainer;
    }

    public HorizontalLayout getComponentHolder() {
        return componentHolder;
    }
}

