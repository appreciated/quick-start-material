package com.github.appreciated.quickstart.material.container;

import com.github.appreciated.quickstart.base.navigation.actions.Action;
import com.github.appreciated.quickstart.base.navigation.actions.CustomAction;
import com.github.appreciated.quickstart.base.navigation.interfaces.*;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.VerticalLayout;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by appreciated on 01.04.2017.
 */
public abstract class MaterialSubPageNavigator extends VerticalLayout implements Subpage, HasSubpages, HasContextActions {

    private MenuBar menuBar;
    private List<Action> subpageActions = new ArrayList<>();
    private LinkedHashMap<Subpage, MenuBar.MenuItem> menuBarItems = new LinkedHashMap<>();
    private String standardStyle;


    public MaterialSubPageNavigator() {
        menuBar = new MenuBar();
        getPagingElements().getSubpages()
                .forEach(subpage -> addSubpage(subpage));
        this.setMargin(false);
        menuBar.setStyleName("borderless custom");
        setCurrentSubpage(menuBarItems.entrySet().stream().map(entry -> entry.getKey()).findFirst().get());
    }

    public void setCurrentSubpage(Subpage page) {
        this.removeAllComponents();
        subpageActions.clear();
        if (page instanceof HasContextActions) {
            subpageActions.addAll(((HasContextActions) page).getContextActions());
        }
        boolean hasPercentageHeight = page instanceof HasPercentageHeight;
        if (page instanceof ContainerSubpage) {
            MaterialNavigationContainerView container = new MaterialNavigationContainerView();
            if (((ContainerSubpage) page).hasPadding()) {
                container.addStyleName("container-padding");
            }
            if (hasPercentageHeight) {
                container.setSizeFull();
                this.setSizeFull();
            }
            container.addComponent(page);
            this.addComponent(container);
        } else {
            if (hasPercentageHeight) {
                page.setSizeFull();
                this.setSizeFull();
            }
            this.addComponent(page);
        }
        updateContextActions();
        menuBarItems.forEach((subpage, menuItem) -> menuItem.setStyleName(page.equals(subpage) ? getStyleName() + "active" : standardStyle));
    }

    @Override
    public boolean showTitle() {
        return false;
    }

    @Override
    public List<Action> getContextActions() {
        ArrayList<Action> list = new ArrayList<>();
        list.add(new CustomAction(menuBar).withInsertLeft(true).withAlignment(Alignment.MIDDLE_LEFT));
        list.addAll(subpageActions);
        return list;
    }

    public void addSubpage(Subpage page) {
        MenuBar.MenuItem item = menuBar.addItem(page.getNavigationName(), page.getNavigationIcon(), (MenuBar.Command) menuItem -> setCurrentSubpage(page));
        standardStyle = item.getStyleName();
        menuBarItems.put(page, item);
    }

    public void removeSubpage(Subpage page) {
        menuBar.removeItem(menuBarItems.get(page));
        menuBarItems.remove(page);
    }

    public void setCaption(Subpage page, String name) {
        menuBarItems.get(page).setText(name);
    }
}