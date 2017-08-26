package com.github.appreciated.quickstart.material.login;

import com.github.appreciated.quickstart.base.authentication.login.AccessControl;
import com.github.appreciated.quickstart.base.authentication.login.LoginListener;
import com.github.appreciated.quickstart.base.authentication.registration.RegistrationControl;
import com.github.appreciated.quickstart.base.authentication.registration.RegistrationResult;
import com.github.appreciated.quickstart.base.listeners.ShortcutKeyListener;
import com.vaadin.data.Binder;
import com.vaadin.data.HasValue;
import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.Component;
import com.vaadin.ui.TextField;

import java.lang.reflect.Field;
import java.util.AbstractMap;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by appreciated on 21.03.2017.
 */
public class LoginView extends LoginViewDesign {

    private RegistrationControl registrationControl;
    private AccessControl accessControl;
    private List<AbstractMap.SimpleEntry<HasValue, Field>> fields;
    private Binder binder;
    private LoginListener loginListener;

    public LoginView() {
        username.focus();
        loginButton.addClickListener(event -> tryLogin());
        addShortcutListener(new ShortcutKeyListener(ShortcutAction.KeyCode.ENTER, (o, o1) -> {
            if (tabs.getSelectedTab() == loginForm) {
                tryLogin();
            } else {
                register();
            }
        }));
    }

    public void setTabsVisible(boolean tabsVisible) {
        tabs.setTabsVisible(tabsVisible);
    }

    public void setLoginVisible(boolean visible) {
        tabs.setSelectedTab(visible ? loginForm : registrationForm);
    }

    public void initRegistrationControl(RegistrationControl registrationControl) {
        this.registrationControl = registrationControl;
        fields = registrationControl.getFields();
        ListIterator<AbstractMap.SimpleEntry<HasValue, Field>> iterator = fields.listIterator(fields.size());
        while(iterator.hasPrevious()) {
            registrationFormLayout.addComponentAsFirst((Component) iterator.previous().getKey());
        }
        binder = registrationControl.getBinderForFields(fields);
        binder.addValueChangeListener(valueChangeListener -> registerButton.setEnabled(binder.isValid()));
        registerButton.addClickListener(clickEvent -> register());
    }

    public void initWithAccessControl(AccessControl accessControl) {
        this.accessControl = accessControl;
    }

    private void register() {
        RegistrationResult result = registrationControl.checkUserRegistrationValidity(binder.getBean());
        if (result.isValid()) {
            tabs.setSelectedTab(loginForm);
        } else {
            TextField field = (TextField) fields.stream().filter(hasValueFieldPair -> hasValueFieldPair.getValue().equals(result)).findFirst().get().getKey();
            field.setComponentError(result.getErrorMessage());
        }
    }

    private void tryLogin() {
        loginButton.setDisableOnClick(true);
        if (accessControl.checkCredentials(username.getValue(), password.getValue())) {
            if (loginListener != null) {
                loginListener.loginSuccessful();
            }
        } else {
            username.focus();
            loginButton.setEnabled(true);
            accessControl.onInvalidCredentials();
        }
    }

    public void setAuthenticationListener(LoginListener loginListener) {
        this.loginListener = loginListener;
    }

}
