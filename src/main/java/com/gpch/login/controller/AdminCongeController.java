package com.gpch.login.controller;


import com.gpch.login.model.Conge;
import com.gpch.login.model.CongeType;
import com.gpch.login.service.CongeService;
import com.gpch.login.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@Controller("/adminconge")
public class AdminCongeController {

    @Autowired
    private CongeService congeService;
    @Autowired
    private UserService userService;
    @RequestMapping(value="/admin/conges/{id}", method = RequestMethod.GET)
    public ModelAndView listConges(@PathVariable int id)  {
        List<Conge> conges = congeService.listCongesByState(id);
        ModelAndView modelAndView = new ModelAndView();
        Map<CongeType,String> names = congeService.getNames();
        modelAndView.addObject("conges", conges);
        modelAndView.addObject("names", names);
        modelAndView.addObject("id",id );
        modelAndView.setViewName("admin/conges");
        return modelAndView;
    }
    @RequestMapping(value="/admin/conges/changestate/{conge_id}/state/{state_id}", method = RequestMethod.GET)
    public String changeState(@PathVariable int conge_id, @PathVariable int state_id)  {

        ModelAndView modelAndView = new ModelAndView();
        Conge conge = congeService.changeState(conge_id,state_id);
        return "redirect:/admin/conges/0";
    }



}
