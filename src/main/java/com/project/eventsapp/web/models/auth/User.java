package com.project.eventsapp.web.models.auth;

import com.project.eventsapp.web.models.enums.roles;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {
    private Long id;
    private String username;
    private String email;
    private String password;
    private roles role;
}
