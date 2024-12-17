package com.project.eventsapp.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.eventsapp.dao.entities.User;

public interface UserRepo extends JpaRepository<User, Long> {
}