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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;


/**
 * Created by Timothy on 9/20/2017.
 */

@Controller
@RequestMapping("menu")
public class MenuController {

    @Autowired
    private MenuDao menuDao;

    @Autowired
    private CheeseDao cheeseDao;

    @RequestMapping(value = "")
    public String index(Model model) {

        model.addAttribute("menus", menuDao.findAll());
        model.addAttribute("title", "My Menus");

        return "menu/index";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String displayAddMenuForm(Model model) {
        model.addAttribute("title", "Add Menu");
        model.addAttribute(new Menu());

        return "menu/add";
    }


    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model,
                      @ModelAttribute @Valid Menu menu, Errors errors) {


        if (errors.hasErrors()) {
            model.addAttribute("title", "Add Menu");
            return "menu/add";
        }


        menuDao.save(menu);
        return "redirect:view/" + menu.getId();
    }


    @RequestMapping(value = "view/{id}", method = RequestMethod.GET)
    public String viewMenu(Model model, @PathVariable int id) {
        model.addAttribute("title", "View Menu");

        model.addAttribute("menu", menuDao.findOne(id));

        return "menu/view";
    }


    @RequestMapping(value = "add-item/{id}", method = RequestMethod.GET)
    public String addItem(Model model, @PathVariable int id) {
        model.addAttribute("title", "Add Menu");

        Menu menu = menuDao.findOne(id);
        AddMenuItemForm menuItemForm = new AddMenuItemForm(menu, cheeseDao.findAll());
        model.addAttribute("form", menuItemForm);
        model.addAttribute("title", "Add item to menu: " + menu.getName());
        return "menu/add-item";
    }



    @RequestMapping(value = "add-item", method = RequestMethod.POST)
    public String addItem(Model model,
                      @ModelAttribute @Valid AddMenuItemForm menuItemForm, Errors errors) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "My Cheeses");
            return "menu/add-item";
        }


        Cheese cheese = cheeseDao.findOne(menuItemForm.getCheeseId());
        Menu menu = menuDao.findOne(menuItemForm.getMenuId());

        menu.addItem(cheese);

        menuDao.save(menu);
        return "redirect:/menu/view/" + menu.getId();
    }
}
