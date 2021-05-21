package com.mAppTime.mAppTimeDomain.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name ="events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long eventId;
    private String title;
    private String description;
    private Long dtStart;
    private Long dtEnd;
    private String jsonData;
    private String pathImage;

    @ManyToMany
    @JoinTable(
            name = "users_events",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    Set<User> users;

}
