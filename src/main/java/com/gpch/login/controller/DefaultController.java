package com.gpch.login.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class DefaultController {
    @RequestMapping("/default")
    public String defaultAfterLogin(HttpServletRequest request) {

        if(request.isUserInRole("ADMIN")){
            return "redirect:/admin/employes";
        }
        if(request.isUserInRole("EMPLOYE")){
            return "redirect:/employes/conges";
        }
        if(request.isUserInRole("CHEF")){
            return "redirect:/chefprojet/employes";
        }
        else {
            return "redirect:/access-denied";
        }
    }
}