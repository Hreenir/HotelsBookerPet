package ru.otus.hotelsbooker.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.EqualsAndHashCode.Exclude;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode
@Entity
@ToString
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Exclude
    private Long id;
    private String name;
    private String city;
    private String country;
    private double rating;
    private String address;
    @OneToMany
    @JoinColumn(name = "hotel")
    private List<Room> rooms = new ArrayList<>();


}
