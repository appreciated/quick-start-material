package com.github.appreciated.quickstart.material.login;

import com.github.appreciated.quickstart.base.authentication.login.AccessControl;
import com.github.appreciated.quickstart.base.authentication.login.LoginListener;
import com.github.appreciated.quickstart.base.authentication.registration.RegistrationControl;
import com.github.appreciated.quickstart.base.navigation.interfaces.theme.LoginImplementation;
import com.github.appreciated.quickstart.material.dialog.MaterialDialog;

/**
 * Created by appreciated on 21.03.2017.
 */
public class LoginImplementationDialog extends MaterialDialog implements LoginImplementation, LoginListener {
    private LoginView view;
    private LoginListener loginListener;

    public LoginImplementationDialog(String title) {
        super(title, new LoginView());
        view = (LoginView) getContent();
        view.setTabsVisible(false);
    }

    public LoginImplementationDialog withLoginVisible(boolean visible) {
        view.setLoginVisible(visible);
        return this;
    }

    @Override
    public LoginImplementationDialog initWithLoginListener(LoginListener loginListener) {
        view.setAuthenticationListener(this);
        this.loginListener = loginListener;
        return this;
    }

    @Override
    public LoginImplementationDialog initWithAccessControl(AccessControl accessControl) {
        view.initWithAccessControl(accessControl);
        return this;
    }

    @Override
    public LoginImplementationDialog initRegistrationControl(RegistrationControl registrationControl) {
        view.initRegistrationControl(registrationControl);
        return this;
    }

    @Override
    public void loginSuccessful() {
        loginListener.loginSuccessful();
        close();
    }
}
