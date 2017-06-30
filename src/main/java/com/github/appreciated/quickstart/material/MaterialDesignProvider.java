package com.github.appreciated.quickstart.material;

import com.github.appreciated.quickstart.base.dialog.Dialog;
import com.github.appreciated.quickstart.base.navigation.interfaces.base.ComponentSubpage;
import com.github.appreciated.quickstart.base.navigation.interfaces.base.ContainerSubpage;
import com.github.appreciated.quickstart.base.navigation.interfaces.components.Pager;
import com.github.appreciated.quickstart.base.navigation.interfaces.components.ProgressStepper;
import com.github.appreciated.quickstart.base.navigation.interfaces.components.SubpageNavigator;
import com.github.appreciated.quickstart.base.navigation.interfaces.theme.LoginImplementationView;
import com.github.appreciated.quickstart.base.navigation.interfaces.theme.NavigationView;
import com.github.appreciated.quickstart.base.navigation.interfaces.theme.QuickStartDesignProvider;
import com.github.appreciated.quickstart.material.components.MaterialNavigationContainerView;
import com.github.appreciated.quickstart.material.components.MaterialPagerView;
import com.github.appreciated.quickstart.material.components.MaterialProgressStepPager;
import com.github.appreciated.quickstart.material.components.MaterialSubpageNavigatorView;
import com.github.appreciated.quickstart.material.design.MaterialDesktopView;
import com.github.appreciated.quickstart.material.design.MaterialMobileView;
import com.github.appreciated.quickstart.material.dialog.MaterialDialog;
import com.github.appreciated.quickstart.material.login.MaterialLoginImplementation;
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
    public LoginImplementationView getLoginView() {
        return new MaterialLoginImplementation();
    }

    @Override
    public Layout getNavigationContainer(ContainerSubpage page) {
        return new MaterialNavigationContainerView(page);
    }

    @Override
    public ComponentSubpage getSubPageNavigator(SubpageNavigator subpages) {
        return new MaterialSubpageNavigatorView(subpages);
    }

    @Override
    public ComponentSubpage getProgressStepper(ProgressStepper subpages) {
        return new MaterialProgressStepPager(subpages);
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
