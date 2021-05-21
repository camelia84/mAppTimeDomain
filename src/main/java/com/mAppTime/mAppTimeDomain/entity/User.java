package com.mAppTime.mAppTimeDomain.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;

    private String contactId;
    private String firstName;
    private String lastName;
    private String numberPhone;

    @ManyToMany(mappedBy = "users")
    Set<Event> events;
}
