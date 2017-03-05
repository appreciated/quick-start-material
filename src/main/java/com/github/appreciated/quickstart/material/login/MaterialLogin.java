package com.github.appreciated.quickstart.material.login;

import com.github.appreciated.quickstart.base.interfaces.LoginListener;
import com.github.appreciated.quickstart.base.interfaces.LoginNavigable;
import com.github.appreciated.quickstart.base.listeners.ShortcutKeyListener;
import com.github.appreciated.quickstart.base.login.AccessControl;
import com.github.appreciated.quickstart.base.navigation.RegistrationControl;
import com.github.appreciated.quickstart.base.notification.QuickNotification;
import com.vaadin.data.Binder;
import com.vaadin.data.HasValue;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.Page;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import javafx.util.Pair;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by appreciated on 07.12.2016.
 */
public class MaterialLogin extends LoginDesign implements LoginNavigable {

    private RegistrationControl registrationControl;
    private LoginListener loginListener;
    private AccessControl accessControl;
    private Binder binder;

    public MaterialLogin() {
        username.focus();
        loginButton.addClickListener(event -> tryLogin());
        this.addShortcutListener(new ShortcutKeyListener(ShortcutAction.KeyCode.ENTER, (o, o1) -> {
            if (tabs.getSelectedTab() == loginForm) {
                tryLogin();
            } else {
                register();
            }
        }));
    }

    @Override
    public void initTitle(String title) {
        this.title.setValue(title);
    }

    @Override
    public void initRegistrationControl(RegistrationControl registrationControl) {
        this.registrationControl = registrationControl;
        List<Pair<HasValue, Field>> fields = registrationControl.getFields();
        fields.forEach(pair -> registrationFormLayout.addComponent((Component) pair.getKey()));
        binder = registrationControl.getBinderForFields(fields);
        registerButton.addClickListener(clickEvent -> register());
    }

    @Override
    public void initWithAccessControl(AccessControl accessControl) {
        this.accessControl = accessControl;
    }

    private void register() {
        registrationControl.onUserDataEntered(binder.getBean());
    }

    private void tryLogin() {
        loginButton.setDisableOnClick(true);
        if (accessControl.signIn(username.getValue(), password.getValue())) {
            loginListener.loginSuccessful();
        } else {
            QuickNotification.showMessage("Login failed", "Please check your username and password and try again.");
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


    public HorizontalLayout getBottomBarWrapper() {
        return bottomBarWrapper;
    }

    public MaterialLogin withBottomBarWrapperContent(Component component) {
        this.bottomBarWrapper.addComponent(component);
        return this;
    }
}
