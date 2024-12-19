package com.project.eventsapp.web.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.project.eventsapp.business.services.EventsServices;
import com.project.eventsapp.business.services.UserServices;
import com.project.eventsapp.dao.entities.Events;

@Controller
@RequestMapping({ "/", "/dashboard" })
public class DashboardController {
    @Autowired
    private EventsServices eventsServices;
    @Autowired
    private UserServices userServices;

    @GetMapping
    public String getDashboard(Model model) {
        Long totalEvents = eventsServices.getNbEvents();
        Long totalUsers = userServices.getNbuser();
        List<Events> lastevents = eventsServices.getEvents();
        System.out.println(lastevents);
        model.addAttribute("eventsTotal", totalEvents);
        model.addAttribute("totalUsers", totalUsers);
        model.addAttribute("lastevents", lastevents);
        return "dashboard";
    }
}
