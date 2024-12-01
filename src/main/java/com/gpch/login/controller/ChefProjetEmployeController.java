package com.gpch.login.controller;


import com.gpch.login.model.User;
import com.gpch.login.service.ChefService;
import com.gpch.login.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller("/chefprojetEnmploye")
public class ChefProjetEmployeController {

    @Autowired
    private UserService userService;

    @RequestMapping(value="/chefprojet/employes", method = RequestMethod.GET)
    public ModelAndView listEmploye()  {
        ModelAndView modelAndView = new ModelAndView();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User currentUser = userService.findUserByEmail(username);
        List<User>  users = userService.findbyChef(currentUser);
        modelAndView.addObject("users", users);
        modelAndView.setViewName("chefprojet/employes");
        return modelAndView;
    }



}
