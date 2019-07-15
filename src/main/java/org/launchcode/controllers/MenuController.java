package org.launchcode.controllers;


import org.launchcode.models.Cheese;
import org.launchcode.models.Menu;
import org.launchcode.models.data.CheeseDao;
import org.launchcode.models.data.MenuDao;
import org.launchcode.models.forms.AddMenuItemForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Controller
@RequestMapping("menu")
public class MenuController {

    @Autowired
    private CheeseDao cheeseDao;

    @Autowired
    private MenuDao menuDao;

    @RequestMapping(value = "")
    public String index(Model model) {
        model.addAttribute("menus", menuDao.findAll());
        model.addAttribute("title", "Menus");
        return "menu/index";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String displayAddMenuForm(Model model) {
        model.addAttribute("title", "Add Menu");
        model.addAttribute(new Menu());
        return "menu/add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String displayAddMenuForm(@ModelAttribute @Valid Menu newMenu,
                                     Errors errors, Model model) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Add Menu");
            return "menu/add";
        }

        menuDao.save(newMenu);
        return "redirect:view/" + newMenu.getId();
    }

    @RequestMapping(value = "view/{menuId}", method = RequestMethod.GET)
    public String viewMenu(Model model, @PathVariable int menuId) {

        Menu menucheeses = menuDao.findOne(menuId);
        model.addAttribute(menucheeses);
        model.addAttribute("title",menucheeses.getName());
        model.addAttribute("cheeses", menucheeses.getCheeses());
        model.addAttribute("menuId", menucheeses.getId());
        return "menu/view";
    }

    @RequestMapping(value = "add-item/{menuId}", method = RequestMethod.GET)
    public String AddItem(Model model, @PathVariable int menuId) {

        Menu menu = menuDao.findOne(menuId);

        AddMenuItemForm form = new AddMenuItemForm(
                cheeseDao.findAll(),
                menu);

        model.addAttribute("title", "Add item to menu: " + menu.getName());
        model.addAttribute("form", form);
        return "menu/add-item";
    }

    @RequestMapping(value = "add-item", method = RequestMethod.POST)
    public String AddItem(@ModelAttribute @Valid AddMenuItemForm form,
                                  Errors errors, Model model) {

        if (errors.hasErrors()) {
            model.addAttribute("form", form);
            return "menu/add-item";
        }

        Cheese somecheese = cheeseDao.findOne(form.getCheeseId());
        Menu somemenu = menuDao.findOne(form.getMenuId());
        somemenu.addItem(somecheese);
        menuDao.save(somemenu);

        return "redirect:/menu/view/" + somemenu.getId();

    }
}
