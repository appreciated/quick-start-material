package com.github.appreciated.quickstart.material;


import com.github.appreciated.quickstart.base.components.DownloadButton;
import com.github.appreciated.quickstart.base.components.UploadButton;
import com.github.appreciated.quickstart.base.interfaces.ContextNavigable;
import com.github.appreciated.quickstart.base.interfaces.NavigationDesignInterface;
import com.github.appreciated.quickstart.base.interfaces.SearchNavigable;
import com.github.appreciated.quickstart.base.navigation.WebsiteNavigator;
import com.github.appreciated.quickstart.base.navigation.actions.Action;
import com.github.appreciated.quickstart.base.navigation.actions.ClickAction;
import com.github.appreciated.quickstart.base.navigation.actions.DownloadAction;
import com.github.appreciated.quickstart.base.navigation.actions.UploadAction;
import com.github.appreciated.quickstart.base.notification.QuickNotification;
import com.github.appreciated.quickstart.base.vaadin.Util;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Page;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;

import java.util.AbstractMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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

            /**
             * Why so complicated you may ask? I did try to use the forEach Method, but it seem removing objects from the
             * inside caused some issues
             */
            StreamSupport.stream(contextButtonWrapper.spliterator(), false).collect(Collectors.toList())
                    .stream()
                    .filter(component -> !(component instanceof MenuBar))
                    .forEach(component -> contextButtonWrapper.removeComponent(component));

            List<Action> actions = contextNavigable.getContextActions();
            if (actions != null && actions.size() > 0) {
                contextButtons.removeItems();
                actions.forEach(action -> {
                    if (action instanceof DownloadAction) {
                        DownloadButton download = new DownloadButton(action.getName(), action.getResource(), (DownloadAction) action);
                        download.setHeight(60, Unit.PIXELS);
                        contextButtonWrapper.addComponent(download);
                    } else if (action instanceof UploadAction) {
                        UploadButton upload = new UploadButton(action.getName(), action.getResource(), (UploadAction) action);
                        upload.setHeight(60, Unit.PIXELS);
                        contextButtonWrapper.addComponent(upload);
                    } else if (action instanceof ClickAction) {
                        contextButtons.addItem(action.getName(), action.getResource(), menuItem -> {
                            ((ClickAction) action).getListener().actionPerformed(null);
                        });
                    }
                });
            }
            contextButtonWrapper.removeStyleName("hidden");
        } else {
            contextButtonWrapper.addStyleName("hidden");
        }
    }

    @Override
    public void setCurrentSearchNavigable(SearchNavigable navigable) {
        if (navigable == null) {
            searchBarWrapper.addStyleName("hidden");
        } else {
            searchBar.setValue("");
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

