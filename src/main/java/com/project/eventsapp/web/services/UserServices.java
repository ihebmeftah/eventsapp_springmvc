package com.project.eventsapp.web.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.project.eventsapp.dao.entities.User;
import com.project.eventsapp.dao.repository.UserRepo;

import jakarta.persistence.EntityNotFoundException;

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

    public Page<User> getUsersPagination(Pageable pegeable) {
        if (pegeable == null) {
            return null;
        }
        return this.userRepo.findAll(pegeable);
    }

    public User getUserById(Long id) {
        if (id == null) {
            return null;
        }
        return this.userRepo
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with id: " + id + " not found"));
    }

    public User updateUser(Long id, User user) {
        User existingUser = this.getUserById(id);
        existingUser.setEmail(user.getEmail());
        existingUser.setUsername(user.getUsername());
        return userRepo.save(existingUser);
    }

    public boolean deleteUser(Long id) {
        userRepo.deleteById(id);
        return true;
    }
}