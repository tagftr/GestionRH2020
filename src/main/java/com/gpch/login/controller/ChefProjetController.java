package com.gpch.login.controller;


import com.gpch.login.dtos.AffectationDto;
import com.gpch.login.model.Projet;
import com.gpch.login.model.User;
import com.gpch.login.service.ProjetService;
import com.gpch.login.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller("/chefprojetProjet")
public class ChefProjetController {


    @Autowired
    private ProjetService projetService;
    @Autowired
    private UserService userService;

    @RequestMapping(value={"/chefprojet/projets"}, method = RequestMethod.GET)
    public ModelAndView listProjets(){
        ModelAndView modelAndView = new ModelAndView();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User currentUser = userService.findUserByEmail(username);
        List<Projet> projets = projetService.findbyChef(currentUser);
        AffectationDto affectation = new AffectationDto();
        modelAndView.addObject("projets", projets);
        modelAndView.addObject("affectation", affectation);
        modelAndView.setViewName("chefprojet/projets");
        return modelAndView;
    }

    @GetMapping("/employetoassign/{id}")
    public ResponseEntity<?> getEmployesToAssign(@PathVariable int id) {
        String result="";
        List<User> users = userService.findbyChefAndNotInProject(id);
        for(User user : users){
            result += "<option value='" + user.getId() +  "'>" + user.getName() + " " +  user.getLastName() + "</option>";
        }
        return ResponseEntity.ok(result);
    }

    @RequestMapping(value="/chefprojet/affecter", method = RequestMethod.POST)
    public ModelAndView affecterProjet( AffectationDto affectation){
        Projet projet = projetService.findById(affectation.getProjet_id());
        User u = userService.findUserById(affectation.getUser_id());
        projetService.affecterUser(u,projet);
        return listProjets();
    }

    @RequestMapping(value="/chefprojet/projetemployes/{id}", method = RequestMethod.GET)
    public ModelAndView listEmployePerProject(@PathVariable int id)  {
        ModelAndView modelAndView = new ModelAndView();
        Projet p = projetService.findById(id);
        Set<User> users = p.getEmployes();
        modelAndView.addObject("users", users);
        modelAndView.addObject("projet", p);
        modelAndView.setViewName("chefprojet/projet-employes");
        return modelAndView;
    }

    @RequestMapping(value="/chefprojet/supprimeremploye/{id}/projet/{projet_id}", method = RequestMethod.GET)
    public String supprimeremploye(@PathVariable int id,@PathVariable int projet_id)  {
        ModelAndView modelAndView = new ModelAndView();
        projetService.supprimerEmploye(projet_id,id);
        return "redirect:/chefprojet/projetemployes/"+projet_id;
    }


}
