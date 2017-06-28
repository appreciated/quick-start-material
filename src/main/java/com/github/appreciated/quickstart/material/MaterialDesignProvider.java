package com.github.appreciated.quickstart.material;

import com.github.appreciated.quickstart.base.dialog.Dialog;
import com.github.appreciated.quickstart.base.navigation.interfaces.base.ComponentSubpage;
import com.github.appreciated.quickstart.base.navigation.interfaces.base.ContainerSubpage;
import com.github.appreciated.quickstart.base.navigation.interfaces.components.Pager;
import com.github.appreciated.quickstart.base.navigation.interfaces.components.ProgressStepper;
import com.github.appreciated.quickstart.base.navigation.interfaces.components.SubpageNavigator;
import com.github.appreciated.quickstart.base.navigation.interfaces.theme.QuickStartDesignProvider;
import com.github.appreciated.quickstart.base.navigation.interfaces.theme.QuickStartLoginView;
import com.github.appreciated.quickstart.base.navigation.interfaces.theme.QuickStartNavigationView;
import com.github.appreciated.quickstart.material.components.MaterialNavigationContainerView;
import com.github.appreciated.quickstart.material.components.MaterialPager;
import com.github.appreciated.quickstart.material.components.MaterialProgressStepPager;
import com.github.appreciated.quickstart.material.components.MaterialSubPageNavigator;
import com.github.appreciated.quickstart.material.design.MaterialDesktopView;
import com.github.appreciated.quickstart.material.design.MaterialMobileView;
import com.github.appreciated.quickstart.material.dialog.MaterialDialog;
import com.github.appreciated.quickstart.material.login.MaterialLogin;
import com.vaadin.ui.Layout;
import org.vaadin.leif.splashscreen.SplashScreen;

/**
 * Created by Johannes on 27.06.2017.
 */
@SplashScreen(value = "com/github/appreciated/quickstart/material/ui/loader.html", width = 100, height = 100)
public class MaterialDesignProvider implements QuickStartDesignProvider {
    @Override
    public Class<? extends QuickStartNavigationView> getMobileDesign() {
        return MaterialMobileView.class;
    }

    @Override
    public Class<? extends QuickStartNavigationView> getDesktopDesign() {
        return MaterialDesktopView.class;
    }

    public QuickStartLoginView getLogin() {
        return new MaterialLogin();
    }

    @Override
    public Layout getNavigationContainer(ContainerSubpage page) {
        MaterialNavigationContainerView container = new MaterialNavigationContainerView();
        if (page.hasPadding()) {
            container.addStyleName("container-padding");
        }
        return container;
    }

    @Override
    public ComponentSubpage getSubPageNavigator(SubpageNavigator subpages) {
        return new MaterialSubPageNavigator(subpages);
    }

    @Override
    public ComponentSubpage getProgressStepper(ProgressStepper subpages) {
        return new MaterialProgressStepPager(subpages);
    }

    @Override
    public ComponentSubpage getPager(Pager subpages) {
        return new MaterialPager(subpages);
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
