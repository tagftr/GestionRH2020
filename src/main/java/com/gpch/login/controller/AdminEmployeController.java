package com.gpch.login.controller;

import com.gpch.login.dtos.EditUserDto;
import com.gpch.login.dtos.UserDto;
import com.gpch.login.model.User;
import com.gpch.login.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;


@Controller("/adminemploye")
public class AdminEmployeController {

    @Autowired
    private UserService userService;

    @RequestMapping(value="/admin/employes", method = RequestMethod.GET)
    public ModelAndView listEmployes()  {

        ModelAndView modelAndView = new ModelAndView();
        List<User> users =userService.findUserByRole("ROLE_EMPLOYE");
        modelAndView.addObject("users", users);
        modelAndView.setViewName("admin/employes");
        return modelAndView;
    }
    @RequestMapping(value="/admin/employes/ajouter", method = RequestMethod.GET)
    public ModelAndView ajouterEmploye(){
        ModelAndView modelAndView = new ModelAndView();
        List<User> chefs = userService.findUserByRole("ROLE_CHEF");
        UserDto user = new UserDto();
        user.setChefs(chefs);
        modelAndView.addObject("user", user);
        modelAndView.setViewName("admin/ajouter-employe");
        return modelAndView;
    }

    @RequestMapping(value="/admin/employes/ajouter", method = RequestMethod.POST)
    public String ajouterEmploye(@Valid @ModelAttribute("user") UserDto userDto, BindingResult result, Model model){

        if (result.hasErrors()){
            userDto.setChefs(userService.findUserByRole("ROLE_CHEF"));
            return "admin/ajouter-employe";
        }
        if(!userDto.getPassword().equals(userDto.getConfirmationPassword())){
            userDto.setChefs(userService.findUserByRole("ROLE_CHEF"));
            result.rejectValue("Password", "error.Password",
                    "Password and password confirmation not matching");
            return "admin/ajouter-employe";
        }
        userService.saveUser(userDto);
        return "redirect:/admin/employes";
    }

    @RequestMapping(value="/admin/employes/modifier/{id}", method = RequestMethod.GET)
    public ModelAndView modifierEmploye(@PathVariable int id){
        User user1 = userService.findUserById(id);
        EditUserDto user;
        user = EditUserDto.convert(user1);
        List<User> chefs =  userService.findUserByRole("ROLE_CHEF");
        ModelAndView modelAndView = new ModelAndView();
        if(user1 != null){
            modelAndView.addObject("user", user);
            modelAndView.addObject("chefs", chefs);
        }
        modelAndView.setViewName("admin/modifier-employe");
        return   modelAndView;
    }

    @RequestMapping(value="/admin/employes/modifier", method = RequestMethod.POST)
    public String modifierEmploye(@Valid @ModelAttribute("user") EditUserDto userDto, BindingResult result, Model model){
        if (result.hasErrors()){
            userDto.setChefs(userService.findUserByRole("ROLE_CHEF"));
            return "admin/modifier-employe";
        }

        User u = userService.modifierEmploye(userDto);
        if(u != null){
            return "redirect:/admin/employes";
        }else
            return "admin/modifier-employe";
    }

    @RequestMapping(value = "/admin/employes/supprimer/{id}", method = RequestMethod.GET)
    public ModelAndView supprimerEmploye(@PathVariable int id){
        userService.supprimerUser(id);
        return listEmployes();
    }



}
