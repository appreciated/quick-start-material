package com.github.appreciated.quickstart.material.component;

import com.github.appreciated.quickstart.base.listeners.LayoutLeftClickListener;
import com.github.appreciated.quickstart.base.listeners.NavigationListener;
import com.github.appreciated.quickstart.base.pages.FinishablePage;
import com.github.appreciated.quickstart.base.pages.Page;
import com.github.appreciated.quickstart.base.pages.attributes.HasFinishablePages;
import com.github.appreciated.quickstart.material.pagemanagers.design.MaterialProgressStepDesign;
import com.vaadin.ui.HorizontalLayout;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by appreciated on 09.03.2017.
 */
public class MaterialProgressStepView extends HorizontalLayout {

    private final List<FinishablePage> pages;
    private boolean navigateable;
    private NavigationListener navigationListener;
    LinkedList<MaterialProgressStepDesign> stepperViews = new LinkedList<>();
    private ListIterator<MaterialProgressStepDesign> currentStepperIterator;
    private MaterialProgressStepDesign currentDesign;

    public MaterialProgressStepView(HasFinishablePages pager, boolean navigatable) {
        this.pages = pager.getPagingElements();
        for (int i = 0; i < pages.size(); i++) {
            MaterialProgressStepDesign view = new MaterialProgressStepDesign();
            if (navigateable) {
                view.addStyleName("v-button");
            }
            stepperViews.add(view);
            view.getStepNumber().setValue(String.valueOf(i + 1));
            view.getStepName().setValue(pages.get(i).getNavigationName());
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

    private void onNavigate(Page page) {
        if (navigationListener != null) {
            navigationListener.onNavigate(page);
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

    private void setActiveStepper(MaterialProgressStepDesign design) {
        if (navigateable) {
            stepperViews.forEach(components1 -> components1.removeStyleName("stepper-wrapper-active"));
        }

        for (MaterialProgressStepDesign stepperView : stepperViews) {
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

    public FinishablePage getCurrent() {
        return pages.get(stepperViews.indexOf(currentDesign));
    }

}
