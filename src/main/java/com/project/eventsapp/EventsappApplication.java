package com.project.eventsapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.project.eventsapp.business.services.UserServices;


@SpringBootApplication
public class EventsappApplication {
	@Autowired
	UserServices userService;

	public static void main(String[] args) {
		SpringApplication.run(EventsappApplication.class, args);
	}

}
