package com.project.eventsapp.web.models.events;

import java.sql.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EventForm {

    private String title;
    private String subtitle;
    private String adress;
    private String description;
    private String photo;
    private Long capacity = 20L;
    private Date date;
}
