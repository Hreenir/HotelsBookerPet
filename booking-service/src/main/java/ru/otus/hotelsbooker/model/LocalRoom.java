package ru.otus.hotelsbooker.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "local_rooms")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class LocalRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @EqualsAndHashCode.Exclude
    private long id;
    @Column(name = "room_number")
    private int roomNumber;
    private boolean enabled;
    @ManyToOne
    @JoinColumn(name = "room")
    private Room room;
}
