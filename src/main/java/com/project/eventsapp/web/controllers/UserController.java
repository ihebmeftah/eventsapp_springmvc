package com.project.eventsapp.web.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.project.eventsapp.web.models.auth.User;
import com.project.eventsapp.web.services.UserServices;

@Controller
public class UserController {
    private List<User> users = new ArrayList<>();
    @Autowired
    private UserServices userServices;

    @GetMapping("users")
    public String getUsersView(Model model) {
        users = userServices.getUsers();
        model.addAttribute("users", users);
        return "users/users";
    }

    @GetMapping("/users/create")
    public String getCreateUsersView(Model model) {
        users = userServices.getUsers();
        model.addAttribute("users", users);
        return "users/create-user";
    }

    @GetMapping("/{id}/update")
    public String getUpdateUsersView(Model model) {
        users = userServices.getUsers();
        model.addAttribute("users", users);
        return "users/update-user";
    }

    @PostMapping(path = "/{id}/delete")
    public String deleteUser(@PathVariable("id") Long id) {
        userServices.deleteUser(id);
        return "redirect:/users";
    }
}
