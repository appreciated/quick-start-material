package com.github.appreciated.quickstart.material;

import com.github.appreciated.quickstart.base.dialog.Dialog;
import com.github.appreciated.quickstart.base.navigation.theme.LoginView;
import com.github.appreciated.quickstart.base.navigation.theme.PageHolder;
import com.github.appreciated.quickstart.base.navigation.theme.QuickStartDesignProvider;
import com.github.appreciated.quickstart.base.pages.NavigatorPage;
import com.github.appreciated.quickstart.base.pages.attributes.PageManager;
import com.github.appreciated.quickstart.base.pages.managed.ContainedPage;
import com.github.appreciated.quickstart.base.pages.managed.Pager;
import com.github.appreciated.quickstart.base.pages.managed.ProgressStepPage;
import com.github.appreciated.quickstart.material.dialog.MaterialDialog;
import com.github.appreciated.quickstart.material.login.MaterialLogin;
import com.github.appreciated.quickstart.material.pagemanagers.MaterialContainerPageView;
import com.github.appreciated.quickstart.material.pagemanagers.MaterialPagerView;
import com.github.appreciated.quickstart.material.pagemanagers.MaterialProgressStepperView;
import com.github.appreciated.quickstart.material.pagemanagers.MaterialSubpagerView;
import com.github.appreciated.quickstart.material.theme.MaterialDesktopView;
import com.github.appreciated.quickstart.material.theme.MaterialMobileView;
import org.vaadin.leif.splashscreen.SplashScreen;

/**
 * Created by appreciated on 27.06.2017.
 */
@SplashScreen(value = "com/github/appreciated/quickstart/material/ui/loader.html", width = 100, height = 100)
public class MaterialDesignProvider implements QuickStartDesignProvider {
    @Override
    public Class<? extends PageHolder> getMobileDesign() {
        return MaterialMobileView.class;
    }

    @Override
    public Class<? extends PageHolder> getDesktopDesign() {
        return MaterialDesktopView.class;
    }

    @Override
    public LoginView getLoginView() {
        return new MaterialLogin();
    }

    @Override
    public PageManager getNavigationContainer(ContainedPage page) {
        return new MaterialContainerPageView(page);
    }

    @Override
    public PageManager getSubpageNavigator(NavigatorPage subpages) {
        return new MaterialSubpagerView(subpages);
    }

    @Override
    public PageManager getProgressStepper(ProgressStepPage subpages) {
        return new MaterialProgressStepperView(subpages);
    }

    @Override
    public PageManager getPager(Pager subpages) {
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
