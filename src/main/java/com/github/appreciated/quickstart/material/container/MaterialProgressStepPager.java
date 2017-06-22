package com.github.appreciated.quickstart.material.container;

import com.github.appreciated.quickstart.base.components.ProgressStepView;
import com.github.appreciated.quickstart.base.navigation.actions.Action;
import com.github.appreciated.quickstart.base.navigation.actions.CustomAction;
import com.github.appreciated.quickstart.base.navigation.interfaces.*;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;

import java.util.Arrays;
import java.util.List;

/**
 * Created by appreciated on 09.03.2017.
 */
public abstract class MaterialProgressStepPager extends VerticalLayout implements Subpage, HasContextActions, HasFinishableSubpages, ProgressStepView.NavigationListener, Finishable.FinishListener {

    private final ProgressStepView progressStepView;
    private final List<Finishable> pages;
    private final List<Action> actions;

    public MaterialProgressStepPager() {
        progressStepView = new ProgressStepView(this, isNavigatable());
        progressStepView.setNavigationListener(this);
        this.pages = getPagingElements();
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
    public void onNavigate(Component next) {
        setNewContent(next);
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

    public void setNewContent(Component content) {
        Component actualContent = null;
        if (content instanceof ContainerSubpage) {
            MaterialNavigationContainerView container = new MaterialNavigationContainerView();
            container.addComponent(content);
            actualContent = container;
        } else {
            actualContent = content;
        }
        this.removeAllComponents();
        this.addComponent(actualContent);
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
