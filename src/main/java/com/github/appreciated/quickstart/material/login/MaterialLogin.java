package com.github.appreciated.quickstart.material.login;

import com.github.appreciated.quickstart.base.authentication.login.AccessControl;
import com.github.appreciated.quickstart.base.authentication.login.LoginListener;
import com.github.appreciated.quickstart.base.authentication.registration.RegistrationControl;
import com.vaadin.server.Page;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;

/**
 * Created by appreciated on 07.12.2016.
 */
public class MaterialLogin extends LoginDesign implements com.github.appreciated.quickstart.base.navigation.theme.LoginView {

    @Override
    public void initTitle(String title) {
        this.title.setValue(title);
    }

    @Override
    public MaterialLogin initWithAccessControl(AccessControl accessControl) {
        loginView.initWithAccessControl(accessControl);
        return this;
    }

    @Override
    public MaterialLogin initRegistrationControl(RegistrationControl registrationControl) {
        loginView.initRegistrationControl(registrationControl);
        return this;
    }

    private void showNotification(Notification notification) {
        notification.setDelayMsec(2000);
        notification.show(Page.getCurrent());
    }

    @Override
    public MaterialLogin initWithLoginListener(LoginListener loginListener) {
        loginView.setAuthenticationListener(loginListener);
        return this;
    }

    public HorizontalLayout getBottomBarWrapper() {
        return bottomBarWrapper;
    }

    public MaterialLogin withBottomBarWrapperContent(Component component) {
        bottomBarWrapper.addComponent(component);
        return this;
    }

}
