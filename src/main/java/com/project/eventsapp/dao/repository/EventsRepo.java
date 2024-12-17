package com.project.eventsapp.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.eventsapp.dao.entities.Events;

public interface EventsRepo extends JpaRepository<Events, Long> {

}
