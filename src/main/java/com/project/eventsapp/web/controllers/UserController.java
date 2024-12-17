package com.project.eventsapp.web.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.eventsapp.dao.entities.User;
import com.project.eventsapp.web.models.auth.RegisterForm;
import com.project.eventsapp.web.models.enums.roles;
import com.project.eventsapp.web.services.UserServices;

@Controller
public class UserController {
    @Autowired
    private UserServices userServices;

    @GetMapping("users")
    public String getUsersView(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int pageSize,
            Model model) {
        Page<User> usersPage = userServices.getUsersPagination(PageRequest.of(page, pageSize));
        model.addAttribute("users", usersPage);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", usersPage.getTotalPages());
        return "users/users";
    }

    // CREATE
    @GetMapping("/users/create")
    public String getCreateUsersView(Model model) {
        model.addAttribute("registerForm", new RegisterForm());
        return "users/create-user";
    }

    @PostMapping("/users/create")
    public String onCreate(Model model, @ModelAttribute RegisterForm registerForm) {
        User user = new User();
        List<roles> r = new ArrayList<>();
        r.add(roles.CLIENT);
        user.setUsername(registerForm.getUsername());
        user.setEmail(registerForm.getEmail());
        user.setPassword(registerForm.getPassword());
        user.setRoles(r);
        user = userServices.createUser(user);
        System.out.println(user.getRoles());
        return "redirect:/users";
    }
    // END CREATE

    // UPDATE
    @GetMapping("/{id}/update")
    public String getUpdateUsersView(@PathVariable Long id, Model model) {
        User user = userServices.getUserById(id);
        model.addAttribute("registerForm",
                new RegisterForm(user.getUsername(), user.getEmail(), user.getPassword()));
        model.addAttribute("id", user.getId());
        System.out.println(user.getPassword());
        return "users/update-user";
    }

    @PostMapping("/{id}/update")
    public String onUpdate(@PathVariable Long id, Model model, @ModelAttribute RegisterForm registerForm) {
        User user = new User();
        user.setUsername(registerForm.getUsername());
        user.setEmail(registerForm.getEmail());
        userServices.updateUser(id, user);
        return "redirect:/users";

    }
    // END UPDATE

    @PostMapping(path = "/{id}/delete")
    public String deleteUser(@PathVariable("id") Long id) {
        userServices.deleteUser(id);
        return "redirect:/users";
    }
}
