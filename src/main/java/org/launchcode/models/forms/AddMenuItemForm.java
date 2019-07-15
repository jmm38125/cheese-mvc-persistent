package org.launchcode.models.forms;

import org.launchcode.models.Cheese;
import org.launchcode.models.Menu;

import javax.validation.constraints.NotNull;

public class AddMenuItemForm {

    @NotNull
    private int menuId;

    @NotNull
    private int cheeseId;

    private Menu menu;

    private Iterable<Cheese> cheeses;


    public int getMenuId() {
        return menuId;
    }

    public int getCheeseId() {
        return cheeseId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }

    public Iterable<Cheese> getCheeses() {
        return cheeses;
    }

    public void setCheeseId(int cheeseId) {
        this.cheeseId = cheeseId;
    }

    public AddMenuItemForm(Iterable<Cheese> cheeses, Menu menu) {
        this.menu = menu;
        this.cheeses = cheeses;
    }

    public Menu getMenu() {
        return menu;
    }

    public AddMenuItemForm(){}
}
