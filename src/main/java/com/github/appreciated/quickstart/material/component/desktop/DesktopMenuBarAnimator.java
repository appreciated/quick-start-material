package com.github.appreciated.quickstart.material.component.desktop;

import com.vaadin.annotations.JavaScript;
import com.vaadin.ui.AbstractJavaScriptComponent;

/**
 * Created by Johannes on 15.04.2017.
 */
@JavaScript({"vaadin://component/desktop_animator_connector.js"})
public class DesktopMenuBarAnimator extends AbstractJavaScriptComponent {

    public void initAnimation() {
        getState().enable = true;
    }

    @Override
    protected DesktopMenuBarAnimatorState getState() {
        return (DesktopMenuBarAnimatorState) super.getState();
    }
}

