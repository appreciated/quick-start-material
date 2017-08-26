package com.github.appreciated.quickstart.material.pagemanagers.design;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.annotations.DesignRoot;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
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
public class MaterialPagerDesign extends VerticalLayout {
    private HorizontalLayout lastWrapper;
    private Button last;
    private HorizontalLayout content;
    private HorizontalLayout nextWrapper;
    private Button next;
    private HorizontalLayout pagerDots;

    public MaterialPagerDesign() {
        Design.read(this);
    }

    public HorizontalLayout getLastWrapper() {
        return lastWrapper;
    }

    public Button getLast() {
        return last;
    }

    public HorizontalLayout getContent() {
        return content;
    }

    public HorizontalLayout getNextWrapper() {
        return nextWrapper;
    }

    public Button getNext() {
        return next;
    }

    public HorizontalLayout getPagerDots() {
        return pagerDots;
    }

}