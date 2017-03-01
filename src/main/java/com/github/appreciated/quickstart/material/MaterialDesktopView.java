package com.github.appreciated.quickstart.material;


import com.github.appreciated.quickstart.base.interfaces.ContextNavigable;
import com.github.appreciated.quickstart.base.interfaces.NavigationDesignInterface;
import com.github.appreciated.quickstart.base.navigation.WebsiteNavigator;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.*;

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
            MenuBar.MenuItem item = this.navigationMenu.addItem(navigation.getNavigationName(), navigation.getNavigationIcon(), menuItem -> {
                System.out.println("Yay");
            });
            this.navigation.addNavigation(item, navigation);
        });
        user.addItem("User", VaadinIcons.USER, menuItem -> {
            System.out.println("Yay");
        });
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
        List<Action> contextIcons = navigable.getContextIcons();
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
                        button.addStyleName("small-context-button");
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
    public WebsiteNavigator getNavigation() {
        return navigation;
    }

    @Override
    public void disableLogout() {
        //user.setVisible(false);
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

