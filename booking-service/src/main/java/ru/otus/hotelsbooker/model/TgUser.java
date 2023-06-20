package ru.otus.hotelsbooker.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;


@Data
@Entity
@Builder
@Table(name = "tgusers")
@NoArgsConstructor
@AllArgsConstructor
public class TgUser {
    @Id
    private long id;
    @OneToOne
    @JoinColumn(name = "role")
    private Role role;
}