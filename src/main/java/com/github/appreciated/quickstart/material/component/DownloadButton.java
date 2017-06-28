package com.github.appreciated.quickstart.base.components;

import com.github.appreciated.quickstart.base.navigation.actions.DownloadAction;
import com.github.appreciated.quickstart.base.ui.QuickStartUI;
import com.vaadin.ui.Button;

/**
 * Created by appreciated on 26.12.2016.
 */
public class DownloadButton extends Button {

    public DownloadButton(DownloadAction action) {

        setIcon(action.getResource());
        if (QuickStartUI.isMobile()) {
            addStyleName("floating-action");
        } else {
            addStyleName("borderless custom");
            setCaption(action.getName());
        }
        action.getDownload().createDownloadButton(this);
        setIcon(action.getResource());
    }
}
