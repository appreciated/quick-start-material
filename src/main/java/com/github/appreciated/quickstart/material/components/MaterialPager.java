package com.github.appreciated.quickstart.material.components;


import com.github.appreciated.quickstart.base.navigation.interfaces.attributes.HasPercentageHeight;
import com.github.appreciated.quickstart.base.navigation.interfaces.base.Subpage;
import com.github.appreciated.quickstart.base.navigation.interfaces.components.Pager;
import com.github.appreciated.quickstart.base.navigation.interfaces.theme.QuickStartPager;
import com.github.appreciated.quickstart.base.ui.QuickStartUI;
import com.vaadin.ui.Button;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by appreciated on 09.12.2016.
 */
public class MaterialPager extends MaterialPagerDesign implements QuickStartPager, HasPercentageHeight {

    private final List<Subpage> subpages;

    private final Map<Subpage, Button> navigablesMap = new HashMap<>();

    private Subpage currentPage;

    public MaterialPager(Pager hasSubpages) {
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
        getLast().setVisible(index != 0);
        getNext().setVisible(index != subpages.size() - 1);
        getContent().removeAllComponents();
        getContent().addComponent(QuickStartUI.getProvider().getComponent(subpage));
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
}
