package com.github.appreciated.quickstart.material.theme;


import com.github.appreciated.quickstart.base.authentication.Util;
import com.github.appreciated.quickstart.base.authentication.login.AccessControl;
import com.github.appreciated.quickstart.base.authentication.registration.RegistrationControl;
import com.github.appreciated.quickstart.base.components.DownloadButton;
import com.github.appreciated.quickstart.base.components.UploadButton;
import com.github.appreciated.quickstart.base.navigation.description.WebAppDescription;
import com.github.appreciated.quickstart.base.navigation.theme.NavigationView;
import com.github.appreciated.quickstart.base.notification.QuickNotification;
import com.github.appreciated.quickstart.base.pages.Subpage;
import com.github.appreciated.quickstart.base.pages.actions.*;
import com.github.appreciated.quickstart.base.pages.attributes.HasContextActions;
import com.github.appreciated.quickstart.base.pages.attributes.HasSearch;
import com.github.appreciated.quickstart.base.ui.QuickStartUI;
import com.github.appreciated.quickstart.material.component.desktop.DesktopMenuBarAnimator;
import com.github.appreciated.quickstart.material.components.design.MaterialDesktopActionBarDesign;
import com.github.appreciated.quickstart.material.login.LoginDialog;
import com.github.appreciated.quickstart.material.theme.design.DesktopNavigationDesign;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.*;

import java.util.AbstractMap;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by appreciated on 04.12.2016.
 */
public class MaterialDesktopView extends DesktopNavigationDesign implements NavigationView {
    public static final String CONFIGURATION_HIDE_ICON = "hide_icon";
    public static final String CONFIGURATION_HIDE_TITLE = "hide_title";
    private AccessControl accessControl;
    private RegistrationControl registrationControl;
    private MaterialDesktopActionBarDesign actionBar = null;

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
        } else if (QuickStartUI.isUserSignedIn()) {
            showUser();
        } else {
            Util.invalidateSession();
        }
    }

    private void showUser() {
        userAuthWrapper.setVisible(false);
        MenuBar.MenuItem item = user.addItem(QuickStartUI.getUsername(), VaadinIcons.USER, null);
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
        user.setVisible(false);
    }

    @Override
    public void setCurrentContainerLabel(String label) {
        actionBar.getContainerLabel().setValue(label);
    }

    @Override
    public void setCurrentActions(HasContextActions contextNavigable) {
        if (contextNavigable != null) {
            List<Action> actions = contextNavigable.getContextActions();
            if (actions != null && actions.size() > 0) {
                actionBar.getContextButtons().removeItems();
                actions.forEach(action -> {
                    if (action instanceof CustomAction) {
                        CustomAction cAction = (CustomAction) action;
                        actionBar.getCustomActionWrapper().addComponent(cAction.getDesktopComponent());
                        actionBar.getCustomActionWrapper().setComponentAlignment(cAction.getDesktopComponent(), cAction.getAlignment());
                    } else if (action instanceof DownloadAction) {
                        DownloadButton download = new DownloadButton((DownloadAction) action);
                        download.setHeight(45, Unit.PIXELS);
                        actionBar.getCustomActionWrapper().addComponent(download);
                        actionBar.getCustomActionWrapper().setComponentAlignment(download, Alignment.MIDDLE_LEFT);
                    } else if (action instanceof UploadAction) {
                        UploadButton upload = new UploadButton((UploadAction) action);
                        upload.setHeight(45, Unit.PIXELS);
                        actionBar.getCustomActionWrapper().addComponent(upload);
                        actionBar.getCustomActionWrapper().setComponentAlignment(upload, Alignment.MIDDLE_LEFT);
                    } else if (action instanceof ClickAction) {
                        actionBar.getContextButtons().addItem(action.getTitle(), action.getIconResource(), menuItem -> {
                            ((ClickAction) action).getListener().actionPerformed(null);
                        });
                    }
                });
                actionBar.getContextButtons().setVisible(actions.stream().filter(action -> action instanceof ClickAction).count() > 0);
            }
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
        actionBar.getContainerLabel().setVisible(visiblity);
    }

    @Override
    public void setCurrentSearchNavigable(HasSearch navigable) {
        if (navigable == null) {
            actionBar.getSearchBarWrapper().setVisible(false);
        } else {
            actionBar.getSearchBar().setValue("");
            actionBar.getSearchBarWrapper().setVisible(true);
            actionBar.getSearchBar().addValueChangeListener(navigable);
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

    @Override
    public Layout getContainerView() {
        return getComponentHolder();
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

    @Override
    public void onNavigate(Subpage subpageComponent) {
        initActionBar();
    }

    @Override
    public void onNavigate(Component subpageComponent) {
        initActionBar();
    }

    private void initActionBar() {
        this.actionbarHolder.removeAllComponents();
        this.actionBar = new MaterialDesktopActionBarDesign();
        this.actionbarHolder.addComponent(actionBar);
    }

    @Override
    public void onComponentAdded(Component currentComponent) {
        this.componentHolder.setComponentAlignment(currentComponent, Alignment.TOP_CENTER);
    }
}

