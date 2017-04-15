package com.github.appreciated.quickstart.material.design;


import com.github.appreciated.quickstart.base.authentication.Util;
import com.github.appreciated.quickstart.base.authentication.login.AccessControl;
import com.github.appreciated.quickstart.base.components.DownloadButton;
import com.github.appreciated.quickstart.base.components.UploadButton;
import com.github.appreciated.quickstart.base.navigation.RegistrationControl;
import com.github.appreciated.quickstart.base.navigation.WebAppDescription;
import com.github.appreciated.quickstart.base.navigation.WebApplicationUI;
import com.github.appreciated.quickstart.base.navigation.actions.*;
import com.github.appreciated.quickstart.base.navigation.interfaces.HasContextActions;
import com.github.appreciated.quickstart.base.navigation.interfaces.HasSearch;
import com.github.appreciated.quickstart.base.navigation.interfaces.NavigationDesignInterface;
import com.github.appreciated.quickstart.base.navigation.interfaces.Subpage;
import com.github.appreciated.quickstart.base.notification.QuickNotification;
import com.github.appreciated.quickstart.material.component.desktop.DesktopMenuBarAnimator;
import com.github.appreciated.quickstart.material.login.LoginDialog;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.*;

import java.util.AbstractMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Created by appreciated on 04.12.2016.
 */
public class MaterialDesktopView extends DesktopNavigationDesign implements NavigationDesignInterface {
    public static final String CONFIGURATION_FULLHEIGHT_NAVIGATIONBAR = "full_height_navigationbar";
    public static final String CONFIGURATION_HIDE_ICON = "hide_icon";
    public static final String CONFIGURATION_HIDE_TITLE = "hide_title";
    private AccessControl accessControl;
    private RegistrationControl registrationControl;

    public MaterialDesktopView() {
        DesktopMenuBarAnimator animator = new DesktopMenuBarAnimator();
        animator.addStyleName("visibility: collapse");
        navigationMenuWrapper.addComponent(animator);
    }

    @Override
    public void initWithConfiguration(Stream<AbstractMap.SimpleEntry<String, Boolean>> configurations) {
        if (configurations != null) {
            configurations.forEach(entry -> {
                if (entry.getValue().booleanValue() == true) {
                    switch (entry.getKey()) {
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

    @Override
    public void initNavigationElements(Stream<Subpage> pages) {
        navigationMenu.removeItems();
        pages.forEach(navigation -> {
            MenuBar.MenuItem item = this.navigationMenu.addItem(navigation.getNavigationName(), navigation.getNavigationIcon(), null);
            item.setCommand(menuItem -> navigation.navigateTo());
        });
    }

    @Override
    public void initWithTitle(String title) {
        this.title.setValue(title);
    }

    @Override
    public void initUserFunctionality(WebAppDescription description) {
        user.removeItems();
        if (description.getLoginNavigable() == null) {
            register.addClickListener(clickEvent -> new LoginDialog("Register").initWithAccessControl(accessControl).initRegistrationControl(registrationControl).withLoginVisible(false).initWithLoginListener(() -> showUser()).show());
            signIn.addClickListener(clickEvent -> new LoginDialog("Sign-In").initWithAccessControl(accessControl).withLoginVisible(true).initWithLoginListener(() -> showUser()).show());
        } else if (WebApplicationUI.isUserSignedIn()) {
            showUser();
        } else {
            Util.invalidateSession();
        }
    }

    private void showUser() {
        userAuthWrapper.setVisible(false);
        MenuBar.MenuItem item = user.addItem(WebApplicationUI.getUsername(), VaadinIcons.USER, null);
        item.addItem("Edit Profile", menuItem -> QuickNotification.showMessageError("This is currently not implemented"));
        item.addItem("Logout", menuItem -> Util.invalidateSession());
    }

    @Override
    public void initCustomMenuElements(List<Component> components) {
        menuElements.removeAllComponents();
        components.forEach(component -> menuElements.addComponent(component));
    }

    @Override
    public AbstractOrderedLayout getHolder() {
        return componentHolder;
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
    public void setCurrentActions(HasContextActions contextNavigable) {
        if (contextNavigable != null) {

            /**
             * Why so complicated you may ask? I did try to use the forEach Method, but it seem removing objects from the
             * inside caused some issues
             */
            StreamSupport.stream(contextButtonWrapper.spliterator(), false).collect(Collectors.toList())
                    .stream()
                    .filter(component -> component != actionWrapper)
                    .forEach(component -> contextButtonWrapper.removeComponent(component));

            StreamSupport.stream(actionWrapper.spliterator(), false).collect(Collectors.toList())
                    .stream()
                    .filter(component -> component != contextButtons)
                    .forEach(component -> actionWrapper.removeComponent(component));

            List<Action> actions = contextNavigable.getContextActions();
            if (actions != null && actions.size() > 0) {
                contextButtons.removeItems();
                actions.forEach(action -> {
                    if (action instanceof CustomAction) {
                        CustomAction cAction = (CustomAction) action;
                        if (!cAction.insertLeft()) {
                            contextButtonWrapper.addComponent(cAction.getDesktopComponent());
                        } else {
                            contextButtonWrapper.addComponentAsFirst(cAction.getDesktopComponent());
                        }
                        contextButtonWrapper.setComponentAlignment(cAction.getDesktopComponent(), cAction.getAlignment());
                    } else if (action instanceof DownloadAction) {
                        DownloadButton download = new DownloadButton((DownloadAction) action);
                        download.setHeight(40, Unit.PIXELS);
                        actionWrapper.addComponent(download);
                        actionWrapper.setComponentAlignment(download, Alignment.MIDDLE_LEFT);
                    } else if (action instanceof UploadAction) {
                        UploadButton upload = new UploadButton((UploadAction) action);
                        upload.setHeight(40, Unit.PIXELS);
                        actionWrapper.addComponent(upload);
                        actionWrapper.setComponentAlignment(upload, Alignment.MIDDLE_LEFT);
                    } else if (action instanceof ClickAction) {
                        contextButtons.addItem(action.getName(), action.getResource(), menuItem -> {
                            ((ClickAction) action).getListener().actionPerformed(null);
                        });
                    }
                });
                contextButtons.setVisible(actions.stream().filter(action -> action instanceof ClickAction).count() > 0);
            }
            contextButtonWrapper.removeStyleName("hidden");
        } else {
            contextButtonWrapper.addStyleName("hidden");
        }
    }

    @Override
    public void allowPercentagePageHeight(boolean allow) {
        if (allow) {
            this.pageWrapper.setHeight(100, Unit.PERCENTAGE);
        } else {
            this.pageWrapper.setHeightUndefined();
        }
    }

    @Override
    public void setPageTitleVisibility(boolean visiblity) {
        containerLabel.setVisible(visiblity);
    }

    @Override
    public void setCurrentSearchNavigable(HasSearch navigable) {
        if (navigable == null) {
            searchBarWrapper.setVisible(false);
        } else {
            searchBar.setValue("");
            searchBarWrapper.setVisible(true);
            searchBar.addValueChangeListener(navigable);
        }
    }

    @Override
    public void initWithAccessControl(AccessControl accessControl) {
        this.accessControl = accessControl;
    }

    @Override
    public void initRegistrationControl(RegistrationControl registrationControl) {
        this.registrationControl = registrationControl;
    }

    /*
     * To make the view more customizable allow every Property to be accessed
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

