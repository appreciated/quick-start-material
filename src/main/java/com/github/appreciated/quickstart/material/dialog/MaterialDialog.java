package com.github.appreciated.quickstart.material.dialog;


import com.github.appreciated.quickstart.base.dialog.Dialog;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Window;

/**
 * Created by appreciated on 14.12.2016.
 */
public class MaterialDialog extends Dialog {

    public MaterialDialog(String title, Component component) {
        this(title, component,null);
    }

    public MaterialDialog(String title, Component component, Button... buttons) {
        super(title, component, buttons);
        Window dialog = getWindow();
        dialog.setResizable(false);
        dialog.setDraggable(false);
        dialog.setModal(true);
        getDialogContentWrapper().setMargin(false);
        getDialogContentWrapper().addStyleName("content-wrapper");
        getButtonWrapper().addStyleName("padding-10");
    }

}
