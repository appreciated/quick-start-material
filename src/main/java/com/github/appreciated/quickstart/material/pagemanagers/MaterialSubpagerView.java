package com.github.appreciated.quickstart.material.pagemanagers;

import com.github.appreciated.quickstart.base.navigation.theme.SubpagerView;
import com.github.appreciated.quickstart.base.pages.NavigatorPage;
import com.github.appreciated.quickstart.base.pages.Page;
import com.github.appreciated.quickstart.base.pages.actions.Action;
import com.github.appreciated.quickstart.base.pages.actions.CustomAction;
import com.github.appreciated.quickstart.base.pages.attributes.HasContextActions;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.MenuBar;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by appreciated on 01.04.2017.
 */
public class MaterialSubpagerView implements SubpagerView {

    private MenuBar menuBar;
    private List<Action> subpageActions = new ArrayList<>();
    private LinkedHashMap<Page, MenuBar.MenuItem> menuBarItems = new LinkedHashMap<>();
    private String standardStyle;
    private NavigatorPage subpages;
    private Page currentSubpage;


    public MaterialSubpagerView(NavigatorPage subpages) {
        this.subpages = subpages;
        menuBar = new MenuBar();
        subpages.getPagingElements().getPages().forEach(subpage -> addSubpage(subpage));
        menuBar.setStyleName("borderless custom");
        currentSubpage = menuBarItems.entrySet().stream().map(entry -> entry.getKey()).findFirst().get();
        setSubpage(currentSubpage);
    }

    @Override
    public Component getComponent() {
        return currentSubpage.getComponent();
    }

    public void navigateTo(Page page) {
        setSubpage(page);
    }

    private void setSubpage(Page page) {
        currentSubpage = page;
        subpageActions.clear();
        if (page instanceof HasContextActions && ((HasContextActions) page).getContextActions() != null) {
            subpageActions.addAll(((HasContextActions) page).getContextActions());
        }
        menuBarItems.forEach((subpage, menuItem) -> menuItem.setStyleName(page.equals(subpage) ? menuItem.getStyleName() + "active" : standardStyle));
        onUpdate();
    }

    @Override
    public boolean showTitle() {
        return false;
    }

    @Override
    public List<Action> getContextActions() {
        ArrayList<Action> list = new ArrayList<>();
        list.add(new CustomAction(menuBar).insertLeft(true).withAlignment(Alignment.MIDDLE_LEFT));
        list.addAll(subpageActions);
        return list;
    }

    public void addSubpage(Page page) {
        MenuBar.MenuItem item = menuBar.addItem(page.getNavigationName(), page.getNavigationIcon(), (MenuBar.Command) menuItem -> setSubpage(page));
        standardStyle = item.getStyleName();
        menuBarItems.put(page, item);
    }

    public void removeSubpage(Page page) {
        menuBar.removeItem(menuBarItems.get(page));
        menuBarItems.remove(page);
    }

    public void setCaption(Page page, String name) {
        menuBarItems.get(page).setText(name);
    }

    public LinkedList<Page> getSubpages() {
        return subpages.getPagingElements().getPages();
    }

}