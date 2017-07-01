package com.github.appreciated.quickstart.material.theme;


import com.github.appreciated.quickstart.base.authentication.Util;
import com.github.appreciated.quickstart.base.authentication.login.AccessControl;
import com.github.appreciated.quickstart.base.authentication.registration.RegistrationControl;
import com.github.appreciated.quickstart.base.components.Helper;
import com.github.appreciated.quickstart.base.navigation.description.WebAppDescription;
import com.github.appreciated.quickstart.base.navigation.theme.PageHolder;
import com.github.appreciated.quickstart.base.pages.Page;
import com.github.appreciated.quickstart.base.pages.actions.*;
import com.github.appreciated.quickstart.base.pages.attributes.HasContextActions;
import com.github.appreciated.quickstart.base.pages.attributes.HasSearch;
import com.github.appreciated.quickstart.material.component.DownloadButton;
import com.github.appreciated.quickstart.material.component.UploadButton;
import com.github.appreciated.quickstart.material.component.mobile.MobileMenuAnimator;
import com.github.appreciated.quickstart.material.pagemanagers.MaterialSubpagerView;
import com.github.appreciated.quickstart.material.theme.design.MobileNavigationDesign;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Created by appreciated on 10.12.2016.
 */
public class MaterialMobileView extends MobileNavigationDesign implements PageHolder {

    public MaterialMobileView() {
        logout.addClickListener(event -> {
            Util.invalidateSession();
        });
        closeSearch.addClickListener(e -> {
            searchbarWrapper.setVisible(false);
            searchbar.clear();
        });

        this.addComponent(new MobileMenuAnimator());
    }

    @Override
    public void initWithTitle(String title) {
        menuButton.setCaption(title);
    }

    @Override
    public void initNavigationElements(Stream<Page> pages) {
        navigationElements.removeAllComponents();
        pages.forEach(element -> {
            if (element instanceof MaterialSubpagerView) {
                addMenuElement(element, true);
                ((MaterialSubpagerView) element).getSubpages().forEach(subpage -> {
                    addChildElement((MaterialSubpagerView) element, subpage, false);
                });
            } else {
                addMenuElement(element, false);
            }
        });
    }

    private void addChildElement(MaterialSubpagerView root, Page page, boolean hasChildren) {
        // Wrapper for the Java script part at the attach() method to not override the vaadin on click events
        HorizontalLayout wrapper = new HorizontalLayout();
        wrapper.setHeight(50, Unit.PIXELS);
        wrapper.setWidth(100, Unit.PERCENTAGE);
        if (hasChildren) {
            wrapper.addStyleName("mobile-tab-wrapper-root");
        } else {
            wrapper.addStyleName("mobile-tab-wrapper");
        }
        Button button = new Button(page.getNavigationName());
        button.addStyleName("mobile-tab borderless");
        button.setSizeFull();
        wrapper.addComponent(button);
        button.addClickListener(clickEvent -> {
            root.navigateTo();
            root.setNewSubpage(page);
        });
        navigationElements.addComponent(wrapper);
    }

    private void addMenuElement(Page page, boolean hasChildren) {
        // Wrapper for the Java script part at the attach() method to not override the vaadin on click events
        HorizontalLayout wrapper = new HorizontalLayout();
        wrapper.setHeight(50, Unit.PIXELS);
        wrapper.setWidth(100, Unit.PERCENTAGE);
        if (hasChildren) {
            wrapper.addStyleName("mobile-tab-wrapper-root");
        } else {
            wrapper.addStyleName("mobile-tab-wrapper");
        }
        Button button = new Button(page.getNavigationName());
        button.addStyleName("mobile-tab");
        button.addStyleName("mobile-tab borderless");
        button.setSizeFull();
        wrapper.addComponent(button);
        button.addClickListener(clickEvent -> page.navigateTo());
        navigationElements.addComponent(wrapper);
    }

    @Override
    public void initUserFunctionality(WebAppDescription description) {

    }

    @Override
    public void initCustomMenuElements(List<Component> components) {
        menuElements.removeAllComponents();
        components.forEach(component -> menuElements.addComponent(component));
    }

    @Override
    public void allowPercentagePageHeight(boolean allow) {
        if (allow) {
            this.setHeight(100, Unit.PERCENTAGE);
            mobileContentWrapper.setHeight(100, Unit.PERCENTAGE);
            this.componentHolder.setHeight(100, Unit.PERCENTAGE);
        } else {
            this.setHeightUndefined();
            mobileContentWrapper.setHeightUndefined();
            this.componentHolder.setHeightUndefined();
        }
    }

    @Override
    public AbstractOrderedLayout getHolder() {
        return componentHolder;
    }

    @Override
    public void disableLogout() {
        logoutWrapper.setVisible(false);
    }

    @Override
    public void setCurrentContainerLabel(String label) {
        menuButton.setCaption(label);
    }

    @Override
    public void setCurrentActions(HasContextActions contextNavigable) {
        if (contextNavigable == null) {
            contextButtonContainer.setVisible(false);
        } else {
            contextButtonWrapper.forEach(component -> {
                if (component != floatingButton) {
                    contextButtonWrapper.removeComponent(component);
                }
            });
            contextButtonContainer.setVisible(true);
            smallContextButtonContainer.removeAllComponents();
            List<Action> actions = contextNavigable.getContextActions();
            Map<Action, Component> generatedButtons = new HashMap<>();
            floatingButton.setIcon(VaadinIcons.ELLIPSIS_V);
            actions.stream().forEach(action -> {
                Component buttonComponent = null;
                if (action instanceof CustomAction) {
                    CustomAction customAction = (CustomAction) action;
                    if (customAction.isMobileCompliant()) {
                        buttonComponent = customAction.getMobileComponent();
                    }
                } else if (action instanceof DownloadAction) {
                    buttonComponent = new DownloadButton((DownloadAction) action);
                } else if (action instanceof UploadAction) {
                    buttonComponent = new HorizontalLayout();
                    ((HorizontalLayout) buttonComponent).addComponent(new UploadButton((UploadAction) action));
                } else if (action instanceof ClickAction) {
                    buttonComponent = new Button(action.getIconResource());
                    ((Button) buttonComponent).addClickListener(clickEvent -> ((ClickAction) action).getListener().actionPerformed(null));
                }
                if (buttonComponent != null) {
                    generatedButtons.put(action, buttonComponent);
                }
            });
            if (generatedButtons.size() > 1) {
                floatingButton.setVisible(true);
                generatedButtons.forEach((action, component) -> {
                    component.setStyleName("context-button");
                    component.setWidth("50px");
                    component.setHeight("50px");
                    smallContextButtonContainer.addComponent(component);
                });
                setStyle(contextButtonContainer, "display-none", false);
                Button.ClickListener clickListener = (Button.ClickListener) clickEvent -> {
                    toggleStyle(smallContextButtonContainer, "display-none");
                };
                floatingButton.addClickListener(clickListener);
            } else {
                floatingButton.setVisible(false);
                generatedButtons.forEach((action, component) -> {
                    component.setStyleName("context-button");
                    component.setWidth("60px");
                    component.setHeight("60px");
                    contextButtonWrapper.addComponent(component);
                });
            }
        }
    }

    @Override
    public void setCurrentSearchNavigable(HasSearch navigable) {
        if (navigable == null) {
            showSearchbarButton.addStyleName("hidden");
            searchbarWrapper.setVisible(false);
        } else {
            showSearchbarButton.removeStyleName("hidden");
            showSearchbarButton.addClickListener((Button.ClickListener) clickEvent -> {
                searchbarWrapper.setVisible(true);
                searchbar.addValueChangeListener(valueChangeEvent -> navigable.valueChange(valueChangeEvent));
                searchbar.focus();
                searchbar.addBlurListener(blurEvent -> searchbarWrapper.setVisible(false));
            });
        }
    }

    @Override
    public void initWithAccessControl(AccessControl accessControl) {

    }

    @Override
    public void initRegistrationControl(RegistrationControl registrationControl) {

    }

    @Override
    public Layout getContainerView() {
        return componentHolder;
    }

    @Override
    public void addPage(Page page) {
        Component component = page.getComponent();
        componentHolder.removeAllComponents();
        allowPercentagePageHeight(Helper.requiresPercentageHeight(component));
        Helper.prepareContainerForComponent(componentHolder, component);
        componentHolder.addComponent(component);
        componentHolder.setComponentAlignment(component, Alignment.TOP_CENTER);
    }

    public Button getMenuButton() {
        return menuButton;
    }

    public VerticalLayout getContextButtonContainer() {
        return contextButtonContainer;
    }

    public VerticalLayout getSmallContextButtonContainer() {
        return smallContextButtonContainer;
    }

    public Button getFloatingButton() {
        return floatingButton;
    }

    public HorizontalLayout getMenuWrapper() {
        return menuWrapper;
    }

    public VerticalLayout getMenu() {
        return menu;
    }

    public HorizontalLayout getLogoutWrapper() {
        return logoutWrapper;
    }

    public Button getLogout() {
        return logout;
    }

    public static void toggleStyle(Component component, String style) {
        if (component.getStyleName().contains(style)) {
            component.removeStyleName(style);
        } else {
            component.addStyleName(style);
        }
    }

    public static void setStyle(Component component, String style, boolean enabled) {
        if (!enabled) {
            component.removeStyleName(style);
        } else {
            component.addStyleName(style);
        }
    }

}
