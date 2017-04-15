package com.github.appreciated.quickstart.material.component.circularprogressbar;

import com.vaadin.annotations.JavaScript;
import com.vaadin.ui.AbstractJavaScriptComponent;

/**
 * Created by Johannes on 10.01.2017.
 */
@JavaScript({"vaadin://component/circularprogressbar_connector.js"})
public class CircularProgressBar extends AbstractJavaScriptComponent {

    public void setProgress(double value) {
        getState().progress = value;
    }

    public double getProgress() {
        return getState().progress;
    }

    @Override
    protected CircularProgressBarState getState() {
        return (CircularProgressBarState) super.getState();
    }

}
