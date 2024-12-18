package com.project.eventsapp.web.controllers;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.project.eventsapp.business.services.EventsServices;
import com.project.eventsapp.dao.entities.Events;
import com.project.eventsapp.web.models.events.EventForm;

@Controller
@RequestMapping("/events")
public class EventController {
    public static String uploadDirectory = System.getProperty("user.dir") + "/src/main/resources/static/img";

    @Autowired
    private EventsServices eventsServices;

    @GetMapping()
    public String getEventsListView(
            @RequestParam(name = "title", required = false) String title,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int pageSize,
            Model model) {
        Page<Events> eventsPage;
        if (title != null) {
            eventsPage = eventsServices.getEventsFilterBytitle(title, PageRequest.of(page, pageSize));
            model.addAttribute("title", title);
        } else {
            eventsPage = eventsServices.getEventsWithPagination(PageRequest.of(page, pageSize));
        }
        model.addAttribute("events", eventsPage);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", eventsPage.getTotalPages());
        return "events/events";
    }

    @GetMapping("/create")
    public String getCreateEventsView(Model model) {
        model.addAttribute("eventForm", new EventForm());
        return "events/create-events";
    }

    @PostMapping("/create")
    public String onCreateEvent(Model model,
            @ModelAttribute EventForm eventForm,
            @RequestParam MultipartFile file) {
        Events event = new Events();
        if (!file.isEmpty()) {
            try {
                StringBuilder fileName = new StringBuilder();
                fileName.append(file.getOriginalFilename());
                Path newFilePath = Paths.get(uploadDirectory, fileName.toString());
                Files.write(newFilePath, file.getBytes());
                event.setPhoto(fileName.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        event.setAdress(eventForm.getAdress());
        event.setCapacity(eventForm.getCapacity());
        event.setDescription(eventForm.getDescription());
        event.setDate(eventForm.getDate());
        event.setTitle(eventForm.getTitle());
        event.setSubtitle(eventForm.getSubtitle());
        eventsServices.createEvent(event);
        return "redirect:/events";
    }

    @GetMapping("/{id}")
    public String getConsultEventsView(@PathVariable Long id, Model model) {
        return "events/consult-event";
    }

    @GetMapping("/{id}/update")
    public String getUpdateEventsView(@PathVariable Long id, Model model) {
        Events event = eventsServices.getEventById(id);
        EventForm eventForm = new EventForm();
        eventForm.setAdress(event.getAdress());
        eventForm.setCapacity(event.getCapacity());
        eventForm.setDescription(event.getDescription());
        eventForm.setDate(event.getDate());
        eventForm.setTitle(event.getTitle());
        eventForm.setSubtitle(event.getSubtitle());
        eventForm.setPhoto(event.getPhoto());
        model.addAttribute("eventForm", eventForm);
        return "events/update-events";
    }

    @PostMapping("/{id}/update")
    public String onUpdateEvent(@PathVariable Long id, Model model, @ModelAttribute EventForm eventForm,
            @RequestParam MultipartFile file) {
        Events event = new Events();
        event.setAdress(eventForm.getAdress());
        event.setCapacity(eventForm.getCapacity());
        event.setDescription(eventForm.getDescription());
        event.setDate(eventForm.getDate());
        event.setTitle(eventForm.getTitle());
        event.setSubtitle(eventForm.getSubtitle());
        event.setPhoto(eventForm.getPhoto());
        if (!file.isEmpty()) {
            StringBuilder fileName = new StringBuilder();
            Path newFilePath = Paths.get(uploadDirectory, file.getOriginalFilename());
            fileName.append(file.getOriginalFilename());
            try {
                Files.write(newFilePath, file.getBytes());
            } catch (Exception e) {
                e.printStackTrace();
            }
            eventForm.setPhoto(fileName.toString());
        }
        eventsServices.updateEvent(id, event);
        return "redirect:/events";
    }

    @PostMapping(path = "/{id}/delete")
    public String deleteUser(@PathVariable("id") Long id) {
        String filename = this.eventsServices.getEventById(id).getPhoto();
        if (filename != null) {
            try {
                Path filePath = Paths.get(uploadDirectory, filename);
                Files.deleteIfExists(filePath);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        eventsServices.deleteEvent(id);
        return "redirect:/events";
    }
}
