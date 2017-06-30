package com.github.appreciated.quickstart.material.components;


import com.github.appreciated.quickstart.base.components.Helper;
import com.github.appreciated.quickstart.base.navigation.interfaces.base.ContainerSubpage;
import com.github.appreciated.quickstart.material.components.design.MaterialNavigationContainerDesign;

/**
 * Created by appreciated on 11.12.2016.
 */
public class MaterialNavigationContainerView extends MaterialNavigationContainerDesign {

    public MaterialNavigationContainerView(ContainerSubpage page) {
        Helper.prepareContainerForComponent(this,page);
        this.addComponent(page);
        if (page.hasPadding()) {
            addStyleName("container-padding");
        }
    }
}
