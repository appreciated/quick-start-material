package com.github.appreciated.quickstart.material.login;

import com.github.appreciated.quickstart.base.interfaces.LoginListener;
import com.github.appreciated.quickstart.base.interfaces.LoginNavigable;
import com.github.appreciated.quickstart.base.listeners.ShortcutKeyListener;
import com.github.appreciated.quickstart.base.login.AccessControl;
import com.github.appreciated.quickstart.base.navigation.WebsiteUI;
import com.github.appreciated.quickstart.base.notification.QuickNotification;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.Page;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;

/**
 * Created by appreciated on 07.12.2016.
 */
public abstract class MaterialLogin extends LoginDesign implements LoginNavigable {

    private LoginListener loginListener;
    private final AccessControl accessControl;

    public MaterialLogin(String loginTitle, AccessControl accessControl) {
        this.accessControl = accessControl;
        username.focus();
        title.setValue(loginTitle);
        loginButton.addClickListener(event -> tryLogin());
        this.addShortcutListener(new ShortcutKeyListener(ShortcutAction.KeyCode.ENTER, (o, o1) -> tryLogin()));
    }

    public MaterialLogin(AccessControl accessControl) {
        this(WebsiteUI.get().getWebsiteDescriptor().getTitle(), accessControl);
    }

    private void tryLogin() {
        loginButton.setDisableOnClick(true);
        if (accessControl.signIn(username.getValue(), password.getValue())) {
            loginListener.loginSuccessful();
        } else {
            QuickNotification.showMessageHumanized("Login failed", "Please check your username and password and try again.");
            username.focus();
            loginButton.setEnabled(true);
        }
    }

    private void showNotification(Notification notification) {
        notification.setDelayMsec(2000);
        notification.show(Page.getCurrent());
    }

    @Override
    public void setAuthenticationListener(LoginListener loginListener) {
        this.loginListener = loginListener;
    }

    @Override
    public AccessControl getAccessControl() {
        return accessControl;
    }

    public HorizontalLayout getBottomBarWrapper() {
        return bottomBarWrapper;
    }

    public MaterialLogin withBottomBarWrapperContent(Component component) {
        this.bottomBarWrapper.addComponent(component);
        return this;
    }
}
