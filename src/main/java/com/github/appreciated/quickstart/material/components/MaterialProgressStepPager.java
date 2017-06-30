package com.github.appreciated.quickstart.material.components;

import com.github.appreciated.quickstart.base.components.Helper;
import com.github.appreciated.quickstart.base.navigation.actions.Action;
import com.github.appreciated.quickstart.base.navigation.actions.CustomAction;
import com.github.appreciated.quickstart.base.navigation.interfaces.Finishable;
import com.github.appreciated.quickstart.base.navigation.interfaces.base.Subpage;
import com.github.appreciated.quickstart.base.navigation.interfaces.components.ProgressStepper;
import com.github.appreciated.quickstart.base.navigation.interfaces.theme.ProgressStepperView;
import com.github.appreciated.quickstart.base.ui.QuickStartUI;
import com.github.appreciated.quickstart.material.component.MaterialProgressStepView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;

import java.util.Arrays;
import java.util.List;

/**
 * Created by appreciated on 09.03.2017.
 */
public class MaterialProgressStepPager extends VerticalLayout implements ProgressStepperView {

    private final MaterialProgressStepView progressStepView;
    private ProgressStepper finishableSubpages;
    private final List<Finishable> pages;
    private final List<Action> actions;

    public MaterialProgressStepPager(ProgressStepper finishableSubpages) {
        progressStepView = new MaterialProgressStepView(finishableSubpages, isNavigatable());
        this.finishableSubpages = finishableSubpages;
        progressStepView.setNavigationListener(this);
        this.pages = finishableSubpages.getPagingElements();
        pages.stream().forEach(subpage -> subpage.setFinishListener(this));
        setMargin(false);
        actions = Arrays.asList(new CustomAction(progressStepView) {
            @Override
            public boolean isMobileCompliant() {
                return false;
            }
        }.withAlignment(Alignment.MIDDLE_CENTER));
    }

    @Override
    public void attach() {
        super.attach();
        progressStepView.setNavigationListener(this);
        setNewContent(progressStepView.getCurrent());
    }

    @Override
    public void onNavigate(Subpage next) {
        setNewContent((Finishable) next);
    }

    @Override
    public void onDone() {
        finishableSubpages.onDone();
    }

    @Override
    public boolean showTitle() {
        return false;
    }

    @Override
    public List<Action> getContextActions() {
        return actions;
    }

    public boolean isNavigatable() {
        return false;
    }

    public void setNewContent(Finishable content) {
        Component component = QuickStartUI.getProvider().getComponent(content);
        Helper.prepareContainerForComponent(this,component);
        this.removeAllComponents();
        this.addComponent(component);
    }

    public void next() {
        progressStepView.next();
    }

    public void previous() {
        progressStepView.previous();
    }

    @Override
    public void onFinish() {
        progressStepView.next();
    }

}
