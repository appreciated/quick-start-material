package com.github.appreciated.quickstart.material.component;

import com.github.appreciated.quickstart.base.pages.actions.ClickAction;
import com.github.appreciated.quickstart.base.ui.QuickStartUI;
import com.vaadin.ui.Button;

public class ClickActionButton extends Button {
    public ClickActionButton(ClickAction action) {
        setIcon(action.getIconResource());
        if (QuickStartUI.isMobile()) {
            addStyleName("floating-action");
        } else {
            addStyleName("borderless custom");
            setCaption(action.getTitle());
        }
        setIcon(action.getIconResource());
        addClickListener(clickEvent -> action.getListener().actionPerformed(null));

    }


}
