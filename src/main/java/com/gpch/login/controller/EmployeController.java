package com.gpch.login.controller;


import com.gpch.login.dtos.AddCongeDto;
import com.gpch.login.dtos.CongeDto;
import com.gpch.login.model.Conge;
import com.gpch.login.model.CongeType;
import com.gpch.login.model.Projet;
import com.gpch.login.model.User;
import com.gpch.login.service.CongeService;
import com.gpch.login.service.ProjetService;
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
import java.util.stream.Collectors;

@Controller("/employe")
public class EmployeController {

    @Autowired
    private CongeService congeService;
    @Autowired
    private UserService userService;
    @Autowired
    private ProjetService projetService;

    @RequestMapping(value={"/employe/projets"}, method = RequestMethod.GET)
    public ModelAndView listProjets(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findUserByEmail(username);
        ModelAndView modelAndView = new ModelAndView();
        List<Projet> projets = projetService.findByUser(user);
        modelAndView.addObject("projets",projets);
        modelAndView.setViewName("employe/projets");
        return modelAndView;
    }

    @RequestMapping(value="/employes/conges", method = RequestMethod.GET)
    public ModelAndView listConges()  {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        List<Conge> conges = congeService.listCongesByUser(username);
        ModelAndView modelAndView = new ModelAndView();
        Map<CongeType,String> names = congeService.getNames();
        modelAndView.addObject("conges", conges);
        modelAndView.addObject("names", names);
        modelAndView.setViewName("employe/conges");
        return modelAndView;
    }

    @RequestMapping(value="/employes/conges/ajouter", method = RequestMethod.GET)
    public ModelAndView ajouterConge() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findUserByEmail(username);
        Map<CongeType, CongeDto> conges =  congeService.getConges(username);
        AddCongeDto conge = new AddCongeDto();
        conge.setListe(conges);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("conge", conge);
        modelAndView.setViewName("employe/ajouter-conge");
        return modelAndView;

    }

    @RequestMapping(value="/employes/conges/ajouter", method = RequestMethod.POST)
    public String ajouterConge(@Valid @ModelAttribute("conge") AddCongeDto addCongeDto, BindingResult result, Model model){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        addCongeDto.setEmail(username);
        congeService.ajouterConge(addCongeDto);
        return "redirect:/employes/conges";
    }

}
