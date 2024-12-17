package com.project.eventsapp.web.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.project.eventsapp.business.services.UserServices;
import com.project.eventsapp.dao.entities.User;
import com.project.eventsapp.web.models.auth.LoginForm;
import com.project.eventsapp.web.models.auth.RegisterForm;
import com.project.eventsapp.web.models.enums.roles;

import org.springframework.web.bind.annotation.PostMapping;

@Controller()
public class AuthController {
    private List<User> users = new ArrayList<>();
    @Autowired
    private UserServices userServices;

    @GetMapping("login")
    public String getLoginView(Model model) {
        model.addAttribute("loginForm", new LoginForm());
        return "authentifcation/login";
    }

    @PostMapping("login")
    public String onLogin(Model model, @ModelAttribute LoginForm loginForm) {
        boolean userFound = users.stream()
                .anyMatch(user -> user.getEmail().equals(loginForm.getEmail())
                        && user.getPassword().equals(loginForm.getPassword()));
        if (userFound) {
            return "redirect:/index";
        }
        return "redirect:/signup";
    }

    @GetMapping("signup")
    public String getSignupView(Model model) {
        model.addAttribute("registerForm", new RegisterForm());
        return "authentifcation/signup";
    }

    @PostMapping("signup")
    public String onSignup(Model model, @ModelAttribute RegisterForm registerForm) {
        User user = new User();
        List<roles> r = new ArrayList<>();
        r.add(roles.CLIENT);
        user.setUsername(registerForm.getUsername());
        user.setEmail(registerForm.getEmail());
        user.setPassword(registerForm.getPassword());
        user.setRoles(r);
        userServices.createUser(user);
        return "redirect:/dashboard";
    }

    @GetMapping("forgot-password")
    public String getForgotPasswordView() {
        return "authentifcation/forgot-password";
    }
}
