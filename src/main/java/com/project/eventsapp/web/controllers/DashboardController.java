package com.project.eventsapp.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.project.eventsapp.business.services.EventsServices;

@Controller
public class DashboardController {
    @Autowired
    private EventsServices eventsServices;

    @GetMapping("dashboard")
    public String getDashboard(Model model) {
        Long total = eventsServices.getNbEvents();
        model.addAttribute("eventsTotal", total);
        return "dashboard";
    }
}
