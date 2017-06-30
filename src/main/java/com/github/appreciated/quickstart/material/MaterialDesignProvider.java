package com.github.appreciated.quickstart.material;

import com.github.appreciated.quickstart.base.dialog.Dialog;
import com.github.appreciated.quickstart.base.navigation.theme.LoginView;
import com.github.appreciated.quickstart.base.navigation.theme.NavigationView;
import com.github.appreciated.quickstart.base.navigation.theme.QuickStartDesignProvider;
import com.github.appreciated.quickstart.base.pages.*;
import com.github.appreciated.quickstart.material.components.MaterialNavigationContainerView;
import com.github.appreciated.quickstart.material.components.MaterialPagerView;
import com.github.appreciated.quickstart.material.components.MaterialProgressStepper;
import com.github.appreciated.quickstart.material.components.MaterialSubpageNavigatorView;
import com.github.appreciated.quickstart.material.dialog.MaterialDialog;
import com.github.appreciated.quickstart.material.login.MaterialLogin;
import com.github.appreciated.quickstart.material.theme.MaterialDesktopView;
import com.github.appreciated.quickstart.material.theme.MaterialMobileView;
import com.vaadin.ui.Layout;
import org.vaadin.leif.splashscreen.SplashScreen;

/**
 * Created by appreciated on 27.06.2017.
 */
@SplashScreen(value = "com/github/appreciated/quickstart/material/ui/loader.html", width = 100, height = 100)
public class MaterialDesignProvider implements QuickStartDesignProvider {
    @Override
    public Class<? extends NavigationView> getMobileDesign() {
        return MaterialMobileView.class;
    }

    @Override
    public Class<? extends NavigationView> getDesktopDesign() {
        return MaterialDesktopView.class;
    }

    @Override
    public LoginView getLoginView() {
        return new MaterialLogin();
    }

    @Override
    public Layout getNavigationContainer(ContainerSubpage page) {
        return new MaterialNavigationContainerView(page);
    }

    @Override
    public ComponentSubpage getSubpageNavigator(SubpageNavigator subpages) {
        return new MaterialSubpageNavigatorView(subpages);
    }

    @Override
    public ComponentSubpage getProgressStepper(ProgressStepper subpages) {
        return new MaterialProgressStepper(subpages);
    }

    @Override
    public ComponentSubpage getPager(Pager subpages) {
        return new MaterialPagerView(subpages);
    }

    @Override
    public Dialog getDialog() {
        return new MaterialDialog();
    }

    @Override
    public SplashScreen getAnnotation() {
        return null;
    }
}
