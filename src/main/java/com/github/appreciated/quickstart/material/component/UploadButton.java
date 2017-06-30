package com.github.appreciated.quickstart.base.components;

import com.github.appreciated.quickstart.base.pages.actions.UploadAction;
import com.github.appreciated.quickstart.base.ui.QuickStartUI;
import com.vaadin.ui.Upload;

/**
 * Created by appreciated on 26.12.2016.
 */
public class UploadButton extends Upload {

    public UploadButton(UploadAction action) {
        if (QuickStartUI.isMobile()) {
            setCaption("");
            setButtonCaption("");
            setSizeFull();
            addStyleName("floating-action");
        } else {
            addStyleName("upload-inline-icon");
            addStyleName("borderless custom");
            setButtonCaption(action.getTitle());
        }
        getButtonCaption();
        setImmediateMode(true);
        setIcon(action.getIconResource());
        action.getUpload().createUploadButton(this);
    }

}
