package com.gpch.login.controller;


import com.gpch.login.dtos.ChefDto;
import com.gpch.login.dtos.EditChefDto;
import com.gpch.login.dtos.EditUserDto;
import com.gpch.login.dtos.UserDto;
import com.gpch.login.model.User;
import com.gpch.login.service.ChefService;
import com.gpch.login.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Controller("/adminchefprojet")
public class AdminChefProjetController {


    @Autowired
    private ChefService chefService;

    @RequestMapping(value="/admin/chefs", method = RequestMethod.GET)
    public ModelAndView listChefProjets()  {
        ModelAndView modelAndView = new ModelAndView();
        List<User> users =chefService.findUserByRole("ROLE_CHEF");
        modelAndView.addObject("users", users);
        modelAndView.setViewName("admin/chefs");
        return modelAndView;
    }
    @RequestMapping(value="/admin/chefs/ajouter", method = RequestMethod.GET)
    public ModelAndView ajouterChef(){
        ModelAndView modelAndView = new ModelAndView();
        ChefDto user = new ChefDto();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("admin/ajouter-chef");
        return modelAndView;
    }

    @RequestMapping(value="/admin/chefs/ajouter", method = RequestMethod.POST)
    public String ajouterChef(@Valid @ModelAttribute("user") ChefDto userDto, BindingResult result, Model model){

        if (result.hasErrors()){
            return "admin/ajouter-chef";
        }
        if(!userDto.getPassword().equals(userDto.getConfirmationPassword())){
            result.rejectValue("Password", "error.Password",
                    "Password and password confirmation not matching");
            return "admin/ajouter-chef";
        }
        chefService.saveChef(userDto);
        return "redirect:/admin/chefs";
    }

    @RequestMapping(value="/admin/chefs/modifier/{id}", method = RequestMethod.GET)
    public ModelAndView modifierChef(@PathVariable int id){
        User user1 = chefService.findUserById(id);
        EditChefDto user;
        user = EditChefDto.convert(user1);
        ModelAndView modelAndView = new ModelAndView();
        if(user1 != null){
            modelAndView.addObject("user", user);
        }
        modelAndView.setViewName("admin/modifier-chef");
        return   modelAndView;
    }

    @RequestMapping(value="/admin/chefs/modifier", method = RequestMethod.POST)
    public String modifierchef(@Valid @ModelAttribute("user") EditChefDto userDto, BindingResult result, Model model){
        if (result.hasErrors()){
            return "admin/modifier-chef";
        }

        User u = chefService.modifierChef(userDto);
        if(u != null){
            return "redirect:/admin/chefs";
        }else
            return "admin/modifier-chef";
    }

    @RequestMapping(value = "/admin/chefs/supprimer/{id}", method = RequestMethod.GET)
    public ModelAndView supprimerChef(@PathVariable int id){
        chefService.supprimerChef(id);
        return listChefProjets();
    }

}
