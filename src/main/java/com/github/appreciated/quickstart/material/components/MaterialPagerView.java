package com.github.appreciated.quickstart.material.components;


import com.github.appreciated.quickstart.base.components.Helper;
import com.github.appreciated.quickstart.base.navigation.theme.PagerView;
import com.github.appreciated.quickstart.base.pages.Page;
import com.github.appreciated.quickstart.base.pages.attributes.PageManager;
import com.github.appreciated.quickstart.base.pages.managed.HorizontalScrollPage;
import com.github.appreciated.quickstart.material.components.design.MaterialPagerDesign;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by appreciated on 09.12.2016.
 */
public class MaterialPagerView extends MaterialPagerDesign implements PagerView, PageManager {

    private final List<Page> pages;

    private final Map<Page, Button> navigablesMap = new HashMap<>();

    private Page currentPage;

    public MaterialPagerView(HorizontalScrollPage hasSubpages) {
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
        }
    }

    public void last() {
        setNewPage(pages.get(pages.indexOf(currentPage) - 1));
    }

    private void setButtonVisible(Component component, boolean visible) {
        if (visible) {
            component.removeStyleName("invisible");
        } else {
            component.addStyleName("invisible");
        }
    }
}
