package com.github.appreciated.quickstart.material.components;


import com.github.appreciated.material.MaterialTheme;
import com.github.appreciated.quickstart.base.components.Helper;
import com.github.appreciated.quickstart.base.pages.ComponentPage;
import com.github.appreciated.quickstart.base.pages.attributes.PageManager;
import com.github.appreciated.quickstart.base.pages.managed.ContainedPage;
import com.github.appreciated.quickstart.material.components.design.MaterialNavigationContainerDesign;
import com.vaadin.ui.Component;

/**
 * Created by appreciated on 11.12.2016.
 */
public class MaterialNavigationContainerView extends MaterialNavigationContainerDesign implements ComponentPage, PageManager {

    public MaterialNavigationContainerView(ContainedPage page) {
        Helper.prepareContainerForComponent(this, page);
        this.addComponent(page);
        if (!page.hasPadding()) {
            addStyleName(MaterialTheme.CARD_NO_PADDING);
        }
    }

    @Override
    public Component getComponent() {
        return this;
    }
}
