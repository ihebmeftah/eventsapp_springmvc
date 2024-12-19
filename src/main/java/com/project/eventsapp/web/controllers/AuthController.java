package com.project.eventsapp.web.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.project.eventsapp.business.services.UserServices;
import com.project.eventsapp.dao.entities.User;
import com.project.eventsapp.web.models.auth.RegisterForm;

@Controller()
public class AuthController {
    @Autowired
    UserServices userServices;

    @GetMapping("/login")
    public String getLoginView(Model model, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            return "redirect:/dashboard";
        }
        return "authentifcation/login";
    }

    @GetMapping("/signup")
    public String getSignupView(Model model) {
        model.addAttribute("registerForm", new RegisterForm());
        return "authentifcation/signup";
    }

    @PostMapping("/signup")
    public String onSignup(Model model, @ModelAttribute RegisterForm registerForm) {
        User user = new User();
        List<String> roles = new ArrayList<>();
        roles.add("admin");
        user.setUsername(registerForm.getUsername());
        user.setEmail(registerForm.getEmail());
        user.setPassword(registerForm.getPassword());
        user.setRoles(roles);
        userServices.saveUser(user);
        return "redirect/login";
    }

    @GetMapping("/access-denied")
    public String getAccessDeniedPage(Model model) {
        model.addAttribute("error", "You are not allowed to access this page");
        return "errors";
    }
}
