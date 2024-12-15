package com.project.eventsapp.web.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.eventsapp.web.models.auth.User;
import com.project.eventsapp.web.repository.UserRepo;

@Service
public class UserServices {
    @Autowired
    private UserRepo userRepo;

    public User createUser(User user) {
        return userRepo.save(user);

    }

    public List<User> getUsers() {
        return userRepo.findAll();
    }

    public User getUserById(Long id) {
        return userRepo.findById(id).get();
    }

    public boolean deleteUser(Long id) {
        userRepo.deleteById(id);
        return true;
    }
}