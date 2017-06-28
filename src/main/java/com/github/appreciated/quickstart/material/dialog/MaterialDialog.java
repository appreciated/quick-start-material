package com.github.appreciated.quickstart.material.dialog;


import com.github.appreciated.quickstart.base.dialog.Dialog;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Window;

/**
 * Created by appreciated on 14.12.2016.
 */
public class MaterialDialog extends Dialog {
    public MaterialDialog() { }

    public MaterialDialog(String title, Component component) {
        this(title, component, null);
    }

    public MaterialDialog(String title, Component component, Button... buttons) {
        super(title, component, buttons);
    }

    public void initDialog() {
        super.initDialog();
        Window dialog = getWindow();
        dialog.setResizable(false);
        dialog.setDraggable(false);
        dialog.setModal(true);
        getDialogContentWrapper().addStyleName("content-wrapper");
        getButtonWrapper().addStyleName("padding-10");
    }

}
