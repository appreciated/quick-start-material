package com.github.appreciated.quickstart.material.component.mobile;

import com.vaadin.annotations.JavaScript;
import com.vaadin.ui.AbstractJavaScriptComponent;

/**
 * Created by Johannes on 15.04.2017.
 */
@JavaScript({"vaadin://component/mobile_animator_connector.js"})
public class MobileMenuAnimator extends AbstractJavaScriptComponent {

    public void initAnimation() {
        getState().enable = true;
    }

    @Override
    protected MobileMenuAnimatorState getState() {
        return (MobileMenuAnimatorState) super.getState();
    }
}

