package com.project.eventsapp.business.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.project.eventsapp.dao.entities.Events;
import com.project.eventsapp.dao.repository.EventsRepo;

@Service
public class EventsServices {
    @Autowired
    private EventsRepo eventsRepo;

    public Long getNbEvents() {
        return eventsRepo.count();
    }

    public List<Events> getEvents() {
        return eventsRepo.findAll();
    }

    public Events getEventById(Long id) {
        if (id == null) {
            return null;
        }
        return eventsRepo.findById(id).get();
    }

    public Page<Events> getEventsFilterBytitle(String title, Pageable pegeable) {
        if (pegeable == null) {
            return null;
        }
        return eventsRepo.findByTitleContainingIgnoreCase(title, pegeable);
    }

    public Page<Events> getEventsWithPagination(Pageable pegeable) {
        if (pegeable == null) {
            return null;
        }
        return this.eventsRepo.findAll(pegeable);
    }

    public boolean deleteEvent(Long id) {
        eventsRepo.deleteById(id);
        return true;
    }

    public Events createEvent(Events e) {
        return eventsRepo.save(e);
    }

    public Events updateEvent(Long id, Events e) {
        Events event = eventsRepo.findById(id).get();
        event.setAdress(e.getAdress());
        event.setCapacity(e.getCapacity());
        event.setDescription(e.getDescription());
        event.setDate(e.getDate());
        event.setTitle(e.getTitle());
        event.setSubtitle(e.getSubtitle());
        event.setPhoto(e.getPhoto());
        return eventsRepo.save(event);
    }
}
