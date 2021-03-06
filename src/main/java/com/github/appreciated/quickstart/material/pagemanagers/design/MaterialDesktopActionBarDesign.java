package com.github.appreciated.quickstart.material.pagemanagers.design;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.annotations.DesignRoot;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.declarative.Design;

/**
 * !! DO NOT EDIT THIS FILE !!
 * <p>
 * This class is generated by Vaadin Designer and will be overwritten.
 * <p>
 * Please make a subclass with logic and additional interfaces as needed,
 * e.g class LoginView extends LoginDesign implements View { }
 */
@DesignRoot
@AutoGenerated
@SuppressWarnings("serial")
public class MaterialDesktopActionBarDesign extends HorizontalLayout {
    private HorizontalLayout wrapper;
    private HorizontalLayout containerLabelWrapper;
    private Label containerLabel;
    private HorizontalLayout customActionWrapper;
    private HorizontalLayout searchBarWrapper;
    private TextField searchBar;

    public MaterialDesktopActionBarDesign() {
        Design.read(this);
    }

    public HorizontalLayout getWrapper() {
        return wrapper;
    }

    public HorizontalLayout getContainerLabelWrapper() {
        return containerLabelWrapper;
    }

    public Label getContainerLabel() {
        return containerLabel;
    }

    public HorizontalLayout getCustomActionWrapper() {
        return customActionWrapper;
    }

    public HorizontalLayout getSearchBarWrapper() {
        return searchBarWrapper;
    }

    public TextField getSearchBar() {
        return searchBar;
    }


}
