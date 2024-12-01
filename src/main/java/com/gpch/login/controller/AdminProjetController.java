package com.gpch.login.controller;


import com.gpch.login.dtos.EditUserDto;
import com.gpch.login.dtos.ProjetDto;
import com.gpch.login.dtos.UserDto;
import com.gpch.login.model.Projet;
import com.gpch.login.model.User;
import com.gpch.login.service.ProjetService;
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
import java.util.Set;

@Controller("/adminprojet")
public class AdminProjetController {

    @Autowired
    private ProjetService projetService;
    @Autowired
    private UserService userService;


    @RequestMapping(value="/admin/projets", method = RequestMethod.GET)
    public ModelAndView listProjets()  {
        ModelAndView modelAndView = new ModelAndView();
        List<Projet> projets =projetService.findAllProjets();
        modelAndView.addObject("projets", projets);
        modelAndView.setViewName("admin/projets");
        return modelAndView;
    }
    @RequestMapping(value="/admin/projets/ajouter", method = RequestMethod.GET)
    public ModelAndView ajouterProjet(){
        ModelAndView modelAndView = new ModelAndView();
        List<User> chefs = userService.findUserByRole("ROLE_CHEF");
        ProjetDto projet = new ProjetDto();
        projet.setChefs(chefs);
        modelAndView.addObject("projet", projet);
        modelAndView.setViewName("admin/ajouter-projet");
        return modelAndView;
    }
    @RequestMapping(value="/admin/projets/ajouter", method = RequestMethod.POST)
    public String ajouterProjet(@Valid @ModelAttribute("projet") ProjetDto projetDto, BindingResult result, Model model){

        if (result.hasErrors()){
            projetDto.setChefs(userService.findUserByRole("ROLE_CHEF"));
            return "admin/ajouter-projet";
        }
        projetService.saveProjet(projetDto);
        return "redirect:/admin/projets";
    }

    @RequestMapping(value="/admin/projets/modifier/{id}", method = RequestMethod.GET)
    public ModelAndView modifierProjet(@PathVariable int id){
        Projet projet1 = projetService.findById(id);
        ProjetDto projetDto = ProjetDto.convert(projet1);
        List<User> chefs =  userService.findUserByRole("ROLE_CHEF");
        Set<User> employes = projet1.getEmployes();
        projetDto.setEmployes(employes);
        projetDto.setChefs(chefs);
        ModelAndView modelAndView = new ModelAndView();
        if(projet1 != null){
            modelAndView.addObject("projet", projetDto);
        }
        modelAndView.setViewName("admin/modifier-projet");
        return   modelAndView;
    }

    @RequestMapping(value="/admin/projets/modifier", method = RequestMethod.POST)
    public String modifierProjet(@Valid @ModelAttribute("user") ProjetDto projetDto, BindingResult result, Model model){
        if (result.hasErrors()){
            projetDto.setChefs(userService.findUserByRole("ROLE_CHEF"));
            return "admin/modifier-projet";
        }

        Projet p = projetService.modifierProjets(projetDto);
        if(p != null){
            return "redirect:/admin/projets";
        }else
            return "admin/modifier-projets";
    }

    @RequestMapping(value = "/admin/projets/supprimer/{id}", method = RequestMethod.GET)
    public ModelAndView supprimerProjet(@PathVariable int id){
        projetService.supprimerProjet(id);
        return listProjets();
    }




}
