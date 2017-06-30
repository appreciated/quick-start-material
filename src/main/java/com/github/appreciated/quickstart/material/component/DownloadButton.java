package com.github.appreciated.quickstart.base.components;

import com.github.appreciated.quickstart.base.pages.actions.DownloadAction;
import com.github.appreciated.quickstart.base.ui.QuickStartUI;
import com.vaadin.ui.Button;

/**
 * Created by appreciated on 26.12.2016.
 */
public class DownloadButton extends Button {

    public DownloadButton(DownloadAction action) {

        setIcon(action.getIconResource());
        if (QuickStartUI.isMobile()) {
            addStyleName("floating-action");
        } else {
            addStyleName("borderless custom");
            setCaption(action.getTitle());
        }
        action.getDownload().createDownloadButton(this);
        setIcon(action.getIconResource());
    }
}
