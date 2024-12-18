package com.project.eventsapp.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.project.eventsapp.business.services.UserServices;
import com.project.eventsapp.web.models.auth.LoginForm;

@Controller()
public class AuthController {
    @Autowired
    UserServices userServices;

    @GetMapping("/login")
    public String getLoginView(Model model, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            return "redirect:/access-denied";
        }
        model.addAttribute("loginForm", new LoginForm());
        return "authentifcation/login";
    }

    @GetMapping("/access-denied")
    public String getAccessDeniedPage(Model model) {
        model.addAttribute("error", "You are not allowed to access this page");
        return "errors";
    }
}
