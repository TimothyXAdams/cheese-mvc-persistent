package org.launchcode.controllers;

import org.launchcode.models.Menu;
import org.launchcode.models.data.CheeseDao;
import org.launchcode.models.data.MenuDao;
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

        model.addAttribute("cheeses", menuDao.findAll());
        model.addAttribute("title", "My Menus");

        return "menu/index";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String displayAddMenuForm(Model model) {
        model.addAttribute("title", "Add Menu");
        model.addAttribute(new Menu());
        //model.addAttribute("cheeseTypes", CheeseType.values());
        //model.addAttribute("categories", categoryDao.findAll());
        return "menu/add";
    }


    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model,
                      @ModelAttribute @Valid Menu menu, Errors errors) {

        //public String processAddCategoryForm(@ModelAttribute @Valid Category newCategory,
        //                                   Errors errors, Model model) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Add Menu");
            return "menu/add";
        }

        //CategoryDao.add(newCategory);
        menuDao.save(menu);
        return "redirect:view/" + menu.getId();
    }


    @RequestMapping(value = "view", method = RequestMethod.GET)
    public String viewMenu(Model model, @PathVariable int id) {
        model.addAttribute("title", "View Menu");
        //model.addAttribute(new Menu());
        //model.addAttribute("cheeseTypes", CheeseType.values());
        //model.addAttribute("menus", menuDao.findOne(id));
        Menu oneMenu = menuDao.findOne(id);
        return "redirect:view/" + oneMenu;
    }


    @RequestMapping(value = "addItem", method = RequestMethod.GET)
    public String addItem(Model model, @PathVariable int id) {
        model.addAttribute("title", "Add Menu");
        //model.addAttribute(new Menu());
        //model.addAttribute("cheeseTypes", CheeseType.values());
        //model.addAttribute("categories", categoryDao.findAll());
        Menu addAnItem = menuDao.findOne(id);
        return "redirect:addItem/" + addAnItem;
    }




}
