package ru.otus.hotelsbooker.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "local_rooms")
@Builder
@Data
public class LocalRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Exclude
    private long id;
    private int roomNumber;
    private boolean enabled;
    @ManyToOne
    @JoinColumn(name = "room")
    private Room room;
}
