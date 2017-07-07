package com.github.appreciated.quickstart.material.pagemanagers;


import com.github.appreciated.quickstart.base.components.Helper;
import com.github.appreciated.quickstart.base.navigation.theme.PagerView;
import com.github.appreciated.quickstart.base.pages.Page;
import com.github.appreciated.quickstart.base.pages.actions.Action;
import com.github.appreciated.quickstart.base.pages.attributes.HasContextActions;
import com.github.appreciated.quickstart.base.pages.managed.Pager;
import com.github.appreciated.quickstart.material.pagemanagers.design.MaterialPagerDesign;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by appreciated on 09.12.2016.
 */
public class MaterialPagerView extends MaterialPagerDesign implements PagerView, HasContextActions {

    private final List<Page> pages;

    private final Map<Page, Button> navigablesMap = new HashMap<>();

    private Page currentPage;

    public MaterialPagerView(Pager hasSubpages) {
        getPagerDots().removeAllComponents();
        pages = hasSubpages.getPagingElements().getPages();
        for (Page page : pages) {
            Button button = new Button();
            button.addStyleName("borderless paging-indicator");
            button.addClickListener(event -> setNewPage(page));
            getPagerDots().addComponent(button);
            navigablesMap.put(page, button);
        }
        getNext().addClickListener(event -> next());
        getLast().addClickListener(event -> last());
        setNewPage(pages.get(0));
        navigablesMap.get(pages.get(0)).addStyleName("paging-indicator-active");
    }

    private void setNewPage(Page page) {
        if (currentPage != null) {
            navigablesMap.get(currentPage).removeStyleName("paging-indicator-active");
        }
        navigablesMap.get(page).addStyleName("paging-indicator-active");
        currentPage = page;

        int index = pages.indexOf(page);
        setButtonVisible(getLastWrapper(), index != 0);
        setButtonVisible(getNextWrapper(), index != pages.size() - 1);
        getContent().removeAllComponents();

        Component component = page.getComponent();
        Helper.prepareContainerForComponent(getContent(), component);
        getContent().addComponent(component);
    }

    public void next() {
        if (pages.indexOf(currentPage) == pages.size() - 1) {
        } else {
            setNewPage(pages.get(pages.indexOf(currentPage) + 1));
            onUpdate(); // to update the context Actions eventually
        }
    }

    public void last() {
        setNewPage(pages.get(pages.indexOf(currentPage) - 1));
        onUpdate(); // to update the context Actions eventually
    }

    private void setButtonVisible(Component component, boolean visible) {
        if (visible) {
            component.removeStyleName("invisible");
        } else {
            component.addStyleName("invisible");
        }
    }

    @Override
    public List<Action> getContextActions() {
        if (currentPage instanceof HasContextActions) {
            return ((HasContextActions) currentPage).getContextActions();
        } else {
            return null;
        }
    }

    @Override
    public boolean showTitle() {
        return currentPage.showTitle();
    }

    @Override
    public String getNavigationName() {
        return currentPage.getNavigationName();
    }
}
