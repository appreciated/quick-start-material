package com.github.appreciated.quickstart.material.components;

import com.github.appreciated.quickstart.base.navigation.actions.Action;
import com.github.appreciated.quickstart.base.navigation.actions.CustomAction;
import com.github.appreciated.quickstart.base.navigation.interfaces.attributes.HasContextActions;
import com.github.appreciated.quickstart.base.navigation.interfaces.base.Subpage;
import com.github.appreciated.quickstart.base.navigation.interfaces.components.SubpageNavigator;
import com.github.appreciated.quickstart.base.navigation.interfaces.theme.SubpagerComponent;
import com.github.appreciated.quickstart.base.ui.QuickStartUI;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.VerticalLayout;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by appreciated on 01.04.2017.
 */
public class MaterialSubPageNavigator extends VerticalLayout implements SubpagerComponent {

    private MenuBar menuBar;
    private List<Action> subpageActions = new ArrayList<>();
    private LinkedHashMap<Subpage, MenuBar.MenuItem> menuBarItems = new LinkedHashMap<>();
    private String standardStyle;
    private SubpageNavigator subpages;


    public MaterialSubPageNavigator(SubpageNavigator subpages) {
        this.subpages = subpages;
        menuBar = new MenuBar();
        subpages.getPagingElements().getSubpages().forEach(subpage -> addSubpage(subpage));
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
        this.addComponent(QuickStartUI.getProvider().getComponent(page));
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

    public LinkedList<Subpage> getSubpages() {
        return subpages.getPagingElements().getSubpages();
    }
}