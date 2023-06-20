package ru.otus.hotelsbooker.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;


@Data
@Entity
@Table(name = "tgusers")
public class TgUser {
    @Id
    private long id;
    @OneToOne
    @JoinColumn(name = "role")
    private Role role;
}