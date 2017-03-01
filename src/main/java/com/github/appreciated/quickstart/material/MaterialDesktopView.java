package com.github.appreciated.quickstart.material;


import com.github.appreciated.quickstart.base.components.DownloadButton;
import com.github.appreciated.quickstart.base.components.UploadButton;
import com.github.appreciated.quickstart.base.interfaces.ContextNavigable;
import com.github.appreciated.quickstart.base.interfaces.NavigationDesignInterface;
import com.github.appreciated.quickstart.base.interfaces.SearchNavigable;
import com.github.appreciated.quickstart.base.navigation.Action;
import com.github.appreciated.quickstart.base.navigation.WebsiteNavigator;
import com.github.appreciated.quickstart.base.notification.QuickNotification;
import com.github.appreciated.quickstart.base.vaadin.Util;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Page;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;

import java.util.AbstractMap;
import java.util.List;

/**
 * Created by appreciated on 04.12.2016.
 */

public class MaterialDesktopView extends DesktopNavigationDesign implements NavigationDesignInterface {
    public static final String CONFIGURATION_FULLHEIGHT_NAVIGATIONBAR = "full_height_navigationbar";
    public static final String CONFIGURATION_HIDE_ICON = "hide_icon";
    public static final String CONFIGURATION_HIDE_TITLE = "hide_title";
    private WebsiteNavigator navigation = null;

    public MaterialDesktopView() {
        navigation = new WebsiteNavigator(this, componentHolder);
        title.setValue(getDefinition().getTitle());
        navigationMenu.removeItems();
        getDefinition().getNavigationElements().stream().forEach(navigation -> {
            MenuBar.MenuItem item = this.navigationMenu.addItem(navigation.getNavigationName(), navigation.getNavigationIcon(), null);
            this.navigation.addNavigation(item, navigation);
        });

        user.removeItems();

        MenuBar.MenuItem item = user.addItem("", VaadinIcons.USER, null);
        item.addItem("Edit Profile", menuItem -> QuickNotification.showMessageError("This is currently not implemented"));
        item.addItem("Logout", menuItem -> Util.invalidateSession());

       /* user.addShortcutListener().addClickListener(event -> {
            Util.invalidateSession();
        });*/
        navigation.navigateTo(getDefinition().getDefaultPage());

        List<AbstractMap.SimpleEntry<String, Boolean>> config = getDefinition().getConfiguration();
        if (config != null) {
            config.stream().forEach(entry -> {
                if (entry.getValue().booleanValue() == true) {
                    switch (entry.getKey()) {
                        case CONFIGURATION_FULLHEIGHT_NAVIGATIONBAR:
                            navigationMenu.setWidth(100, Unit.PERCENTAGE);
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
    }

    private void initContextButtons(ContextNavigable navigable) {
        /*smallContextButtonContainer.removeAllComponents();
        List<Action> contextIcons = navigable.getContextActions();
        List<HashMap.SimpleEntry<Resource, Component>> generatedButtons = new ArrayList<>();

        if (contextIcons.size() > 1) {
            //this.isExpandButton = true;
            contextButton.setIcon(FontAwesome.ELLIPSIS_V);
            contextIcons.stream().forEach(action -> {
                Component buttonComponent = null;
                switch (action.getActionType()) {
                    case DOWNLOAD:
                        buttonComponent = new DownloadButton(action);
                        ((DownloadButton) buttonComponent).addClickListener(clickEvent -> navigable.onContextButtonClicked(action.getResource()));
                        break;
                    case BUTTON:
                        Button button = new Button(action.getResource());
                        button.addClickListener(clickEvent -> navigable.onContextButtonClicked(action.getResource()));
                        button.addStyleName("mobile-context-button");
                        buttonComponent = button;
                        break;
                    case UPLOAD:
                        buttonComponent = new UploadButton(action);
                        break;
                }
                smallContextButtonContainer.addComponent(buttonComponent);
                generatedButtons.add(new AbstractMap.SimpleEntry<>(action.getResource(), buttonComponent));
            });
        } else {
            this.isExpandButton = false;
            contextButton.setIcon(contextIcons.get(0).getResource());
            generatedButtons.add(new AbstractMap.SimpleEntry<>(contextIcons.get(0).getResource(), contextButton));
        }
        setStyle(contextButtonContainer, "display-none", false);

        if (clickListener != null) {
            contextButton.removeClickListener(clickListener);
        }
        clickListener = (Button.ClickListener) clickEvent -> {
            if (smallContextButtonContainer != null && !isExpandButton) {
                contextButtonListener.onContextButtonClick(contextButton.getIcon());
            } else if (isExpandButton) {
                toggleStyle(smallContextButtonContainer, "display-none");
            }
        };
        contextButton.addClickListener(clickListener);
        navigable.generatedButtons(generatedButtons);*/
    }

    @Override
    public void attach() {
        super.attach();
        Page.getCurrent().getJavaScript().execute("var element = document.getElementById('contentPanel');\n" +
                "var childElement = element.getElementsByClassName('v-panel-content').item(0);\n" +
                "console.log('test');\n" +
                "childElement.addEventListener('scroll', function () {\n" +
                "    if (document.getElementById('navigation-bar')) {\n" +
                "        if (childElement.scrollTop > 0) {\n" +
                "            if (!document.getElementById('navigation-bar').classList.contains('floating-navigation-bar')) {\n" +
                "                document.getElementById('navigation-bar').classList.add('floating-navigation-bar')\n" +
                "                console.log('added');\n" +
                "            }\n" +
                "        }\n" +
                "        else {\n" +
                "            if (document.getElementById('navigation-bar').classList.contains('floating-navigation-bar')) {\n" +
                "                document.getElementById('navigation-bar').classList.remove('floating-navigation-bar');\n" +
                "                console.log('removed');\n" +
                "            }\n" +
                "        }\n" +
                "    }\n" +
                "    else {\n" +
                "        console.log('no element');\n" +
                "    }\n" +
                "});");
    }

    @Override
    public WebsiteNavigator getNavigation() {
        return navigation;
    }

    @Override
    public void disableLogout() {
        //user.setVisible(false);
    }

    @Override
    public void setCurrentContainerLabel(String label) {
        containerLabel.setValue(label);
    }

    @Override
    public void setCurrentActions(ContextNavigable contextNavigable) {
        if (contextNavigable != null) {
            List<Action> actions = contextNavigable.getContextActions();
            if (actions == null || actions.size() == 0) {
                actions.forEach(action -> {
                    switch (action.getActionType()) {
                        case DOWNLOAD:
                            contextButtonWrapper.addComponent(new DownloadButton(action));
                            break;
                        case UPLOAD:
                            contextButtonWrapper.addComponent(new UploadButton(action));
                            break;
                        default:
                            contextButtons.addItem(action.getName(), action.getResource(), menuItem -> {
                                action.getListener().actionPerformed(null);
                            });
                            break;
                    }
                });
            }
        } else {
            contextButtonWrapper.addStyleName("hidden");
        }
    }

    @Override
    public void setCurrentSearchNavigable(SearchNavigable navigable) {
        if (navigable == null) {
            searchBarWrapper.addStyleName("hidden");
        } else {
            searchBarWrapper.removeStyleName("hidden");
            searchBar.addValueChangeListener(navigable);
        }
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

    public MenuBar getUser() {
        return user;
    }

    public HorizontalLayout getComponentHolder() {
        return componentHolder;
    }
}

