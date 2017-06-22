package com.github.appreciated.quickstart.base.components;

import com.github.appreciated.quickstart.base.listeners.LayoutLeftClickListener;
import com.github.appreciated.quickstart.base.navigation.interfaces.Finishable;
import com.github.appreciated.quickstart.base.navigation.interfaces.HasFinishableSubpages;
import com.github.appreciated.quickstart.base.navigation.interfaces.Subpage;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by appreciated on 09.03.2017.
 */
public class ProgressStepView extends HorizontalLayout {

    private final List<Finishable> pages;
    private boolean navigateable;
    private NavigationListener navigationListener;
    LinkedList<ProgressStepDesign> stepperViews = new LinkedList<>();
    private ListIterator<ProgressStepDesign> currentStepperIterator;
    private ProgressStepDesign currentDesign;

    public ProgressStepView(HasFinishableSubpages pager, boolean navigatable) {
        this.pages = pager.getPagingElements();
        for (int i = 0; i < pages.size(); i++) {
            ProgressStepDesign view = new ProgressStepDesign();
            if (navigateable) {
                view.addStyleName("v-button");
            }
            stepperViews.add(view);
            view.stepNumber.setValue(String.valueOf(i + 1));
            view.stepName.setValue(pages.get(i).getNavigationName());
            int finalI = i;
            if (navigateable) {
                view.addLayoutClickListener(new LayoutLeftClickListener(clickEvent -> {
                    view.addStyleName("stepper-wrapper-active");
                    onNavigate(pages.get(finalI));
                }));
            }
            this.addComponent(view);
        }
        addStyleName("stepper-view");
        setSpacing(true);
        currentStepperIterator = stepperViews.listIterator(1);
        setActiveStepper(stepperViews.getFirst());
    }

    private void onNavigate(Subpage subpage) {
        if (navigationListener != null) {
            navigationListener.onNavigate(subpage);
        }
    }

    public void previous() {
        if (currentStepperIterator.hasPrevious()) {
            setActiveStepper(currentStepperIterator.previous());
        }
    }

    public void next() {
        if (currentStepperIterator.hasNext()) {
            setActiveStepper(currentStepperIterator.next());
        } else {
            navigationListener.onDone();
        }
    }

    private void setActiveStepper(ProgressStepDesign design) {
        if (navigateable) {
            stepperViews.forEach(components1 -> components1.removeStyleName("stepper-wrapper-active"));
        }

        for (ProgressStepDesign stepperView : stepperViews) {
            stepperView.addStyleName("stepper-wrapper-active");
            if (stepperView == design) {
                break;
            }
        }
        currentDesign = design;
        onNavigate(pages.get(stepperViews.indexOf(currentDesign)));
    }

    public void setNavigationListener(NavigationListener navigationListener) {
        this.navigationListener = navigationListener;
    }

    public void reInit() {
        setActiveStepper(stepperViews.getFirst());
    }

    public Finishable getCurrent() {
        return pages.get(stepperViews.indexOf(currentDesign));
    }

    public interface NavigationListener {
        void onNavigate(Component next);

        void onDone();
    }
}
