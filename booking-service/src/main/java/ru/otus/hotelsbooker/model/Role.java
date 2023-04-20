package ru.otus.hotelsbooker.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;

import java.util.Set;

@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @EqualsAndHashCode.Exclude
    private long id;
    private String name;
    @ManyToMany(mappedBy = "roles")
    private Set<User> users;

}
