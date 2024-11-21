package com.project.eventsapp.web.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.project.eventsapp.web.models.auth.LoginForm;
import com.project.eventsapp.web.models.auth.RegisterForm;
import com.project.eventsapp.web.models.auth.User;
import com.project.eventsapp.web.models.enums.roles;
import org.springframework.web.bind.annotation.PostMapping;

@Controller()
public class AuthController {

    private static List<User> users = new ArrayList<User>();
    private static Long idCount = 0L;

    static {
        users.add(new User(++idCount, "admin", "admin@admin.com", "admin", roles.ADMIN));
        users.add(new User(++idCount, "client", "client@admin.com", "client", roles.CLIENT));

    }

    @GetMapping("index")
    public String getIndexView(Model model) {
        return "index";
    }

    @GetMapping("login")
    public String getLoginView(Model model) {
        model.addAttribute("loginForm", new LoginForm());
        return "login";
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
        return "signup";
    }

    @PostMapping("signup")
    public String onSignup(Model model, @ModelAttribute RegisterForm registerForm) {
        users.add(
                new User(
                        ++idCount,
                        registerForm.getUsername(),
                        registerForm.getEmail(),
                        registerForm.getPassword(),
                        roles.CLIENT));
        return "redirect:/login";
    }

    @GetMapping("forgot-password")
    public String getForgotPasswordView() {
        return "forgot-password";
    }
}
