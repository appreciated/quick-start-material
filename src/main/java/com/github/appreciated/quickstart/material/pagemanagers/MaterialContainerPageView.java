package com.github.appreciated.quickstart.material.pagemanagers;


import com.github.appreciated.material.MaterialTheme;
import com.github.appreciated.quickstart.base.components.Helper;
import com.github.appreciated.quickstart.base.navigation.theme.ContainerPageView;
import com.github.appreciated.quickstart.base.pages.Page;
import com.github.appreciated.quickstart.base.pages.managed.ContainedPage;
import com.github.appreciated.quickstart.material.pagemanagers.design.MaterialNavigationContainerDesign;
import com.vaadin.ui.Component;

/**
 * Created by appreciated on 11.12.2016.
 */
public class MaterialContainerPageView extends MaterialNavigationContainerDesign implements ContainerPageView {

    private ContainedPage page;

    public MaterialContainerPageView(ContainedPage page) {
        this.page = page;
        Helper.prepareContainerForComponent(this, page);
        this.addComponent(page);
        if (!page.hasPadding()) {
            addStyleName(MaterialTheme.CARD_NO_PADDING);
        }
    }


    public Component getViewComponent() {
        return this;
    }

    @Override
    public Page getContainedPage() {
        return page;
    }
}
