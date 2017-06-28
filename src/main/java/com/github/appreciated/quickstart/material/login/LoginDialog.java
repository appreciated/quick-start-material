package com.github.appreciated.quickstart.material.login;

import com.github.appreciated.quickstart.base.authentication.login.AccessControl;
import com.github.appreciated.quickstart.base.authentication.login.LoginListener;
import com.github.appreciated.quickstart.base.authentication.registration.RegistrationControl;
import com.github.appreciated.quickstart.base.navigation.interfaces.theme.QuickStartLogin;
import com.github.appreciated.quickstart.material.dialog.MaterialDialog;

/**
 * Created by appreciated on 21.03.2017.
 */
public class LoginDialog extends MaterialDialog implements QuickStartLogin, LoginListener {
    private LoginView view;
    private LoginListener loginListener;

    public LoginDialog(String title) {
        super(title, new LoginView());
        view = (LoginView) getContent();
        view.setTabsVisible(false);
    }

    public LoginDialog withLoginVisible(boolean visible) {
        view.setLoginVisible(visible);
        return this;
    }

    @Override
    public LoginDialog initWithLoginListener(LoginListener loginListener) {
        view.setAuthenticationListener(this);
        this.loginListener = loginListener;
        return this;
    }

    @Override
    public LoginDialog initWithAccessControl(AccessControl accessControl) {
        view.initWithAccessControl(accessControl);
        return this;
    }

    @Override
    public LoginDialog initRegistrationControl(RegistrationControl registrationControl) {
        view.initRegistrationControl(registrationControl);
        return this;
    }

    @Override
    public void loginSuccessful() {
        loginListener.loginSuccessful();
        close();
    }
}
