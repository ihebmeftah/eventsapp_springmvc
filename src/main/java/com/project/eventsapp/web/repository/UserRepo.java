package com.project.eventsapp.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.eventsapp.web.models.auth.User;

public interface UserRepo extends JpaRepository<User, Long> {
}
