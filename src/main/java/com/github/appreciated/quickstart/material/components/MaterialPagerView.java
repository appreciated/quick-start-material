package com.github.appreciated.quickstart.material.components;


import com.github.appreciated.quickstart.base.components.Helper;
import com.github.appreciated.quickstart.base.navigation.theme.PagerView;
import com.github.appreciated.quickstart.base.pages.Pager;
import com.github.appreciated.quickstart.base.pages.Subpage;
import com.github.appreciated.quickstart.base.ui.QuickStartUI;
import com.github.appreciated.quickstart.material.components.design.MaterialPagerDesign;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by appreciated on 09.12.2016.
 */
public class MaterialPagerView extends MaterialPagerDesign implements PagerView {

    private final List<Subpage> subpages;

    private final Map<Subpage, Button> navigablesMap = new HashMap<>();

    private Subpage currentPage;

    public MaterialPagerView(Pager hasSubpages) {
        getPagerDots().removeAllComponents();
        subpages = hasSubpages.getPagingElements().getSubpages();
        for (Subpage subpage : subpages) {
            Button button = new Button();
            button.addStyleName("borderless paging-indicator");
            button.addClickListener(event -> setNewPage(subpage));
            getPagerDots().addComponent(button);
            navigablesMap.put(subpage, button);
        }
        getNext().addClickListener(event -> next());
        getLast().addClickListener(event -> last());
        setNewPage(subpages.get(0));
        navigablesMap.get(subpages.get(0)).addStyleName("paging-indicator-active");
    }

    private void setNewPage(Subpage subpage) {
        if (currentPage != null) {
            navigablesMap.get(currentPage).removeStyleName("paging-indicator-active");
        }
        navigablesMap.get(subpage).addStyleName("paging-indicator-active");
        currentPage = subpage;

        int index = subpages.indexOf(subpage);
        setButtonVisible(getLastWrapper(), index != 0);
        setButtonVisible(getNextWrapper(), index != subpages.size() - 1);
        getContent().removeAllComponents();

        Component component = QuickStartUI.getProvider().getComponent(subpage);
        Helper.prepareContainerForComponent(getContent(), component);
        getContent().addComponent(component);
    }

    public void next() {
        if (subpages.indexOf(currentPage) == subpages.size() - 1) {
        } else {
            setNewPage(subpages.get(subpages.indexOf(currentPage) + 1));
        }
    }

    public void last() {
        setNewPage(subpages.get(subpages.indexOf(currentPage) - 1));
    }

    public void onFinish() {
    }

    private void setButtonVisible(Component component, boolean visible) {
        if (visible) {
            component.removeStyleName("invisible");
        } else {
            component.addStyleName("invisible");
        }
    }
}
