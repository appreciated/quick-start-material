package com.github.appreciated.quickstart.material.container;


import com.github.appreciated.quickstart.base.navigation.container.Pager;
import com.github.appreciated.quickstart.base.navigation.interfaces.ContainerSubpage;
import com.github.appreciated.quickstart.base.navigation.interfaces.HasPercentageHeight;
import com.github.appreciated.quickstart.base.navigation.interfaces.Subpage;
import com.vaadin.ui.Button;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by appreciated on 09.12.2016.
 */
public abstract class MaterialPager extends MaterialPagerDesign implements Pager, HasPercentageHeight {

    private final List<Subpage> subpages;

    private final Map<Subpage, Button> navigablesMap = new HashMap<>();

    private Subpage currentPage;

    public MaterialPager() {
        pagerDots.removeAllComponents();
        subpages = getPagingElements().getSubpages();
        for (Subpage subpage : subpages) {
            Button button = new Button();
            button.addStyleName("paging-indicator");
            button.addClickListener(event -> setNewPage(subpage));
            pagerDots.addComponent(button);
            navigablesMap.put(subpage, button);
        }
        next.addClickListener(event -> next());
        last.addClickListener(event -> last());
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
        last.setVisible(index != 0);
        next.setVisible(index != subpages.size() - 1);
        if (subpage instanceof ContainerSubpage) {
            MaterialNavigationContainerView container = new MaterialNavigationContainerView();
            container.addComponent(subpage);
            content.removeAllComponents();
            content.addComponent(container);
        } else {
            content.removeAllComponents();
            content.addComponent(subpage);
        }
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
