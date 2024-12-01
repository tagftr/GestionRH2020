package com.gpch.login.controller;


import com.gpch.login.dtos.AddCongeDto;
import com.gpch.login.dtos.CongeDto;
import com.gpch.login.dtos.EditChefDto;
import com.gpch.login.model.Conge;
import com.gpch.login.model.CongeType;
import com.gpch.login.model.Projet;
import com.gpch.login.model.User;
import com.gpch.login.service.CongeService;
import com.gpch.login.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Controller("/chefprojetconge")
public class ChefProjetCongeController {

    @Autowired
    private CongeService congeService;
    @Autowired
    private UserService userService;


    @RequestMapping(value="/chefprojet/conges", method = RequestMethod.GET)
    public ModelAndView listConges()  {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        List<Conge> conges = congeService.listCongesByUser(username);
        ModelAndView modelAndView = new ModelAndView();
        Map<CongeType,String> names = congeService.getNames();
        modelAndView.addObject("conges", conges);
        modelAndView.addObject("names", names);
        modelAndView.setViewName("chefprojet/conges");
        return modelAndView;
    }

    @RequestMapping(value="/chefprojet/ajouter", method = RequestMethod.GET)
    public ModelAndView ajouterConge() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findUserByEmail(username);
        Map<CongeType, CongeDto> conges =  congeService.getConges(username);
        AddCongeDto conge = new AddCongeDto();
        conge.setListe(conges);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("conge", conge);
        modelAndView.setViewName("chefprojet/ajouter-conge");
        return modelAndView;

    }

    @RequestMapping(value="/chefprojet/ajouter", method = RequestMethod.POST)
    public String ajouterConge(@Valid @ModelAttribute("conge") AddCongeDto addCongeDto, BindingResult result, Model model){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        addCongeDto.setEmail(username);
        congeService.ajouterConge(addCongeDto);
        return "redirect:/chefprojet/conges";
    }






}
