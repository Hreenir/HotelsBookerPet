package ru.otus.hotelsbooker.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.EqualsAndHashCode.Exclude;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Exclude
    private Long id;
    private String name;
    private String city;
    private String country;
    private double rating;
    private String address;
    @OneToMany
    @JoinColumn(name = "hotel")
    private List<Room> rooms;


}
