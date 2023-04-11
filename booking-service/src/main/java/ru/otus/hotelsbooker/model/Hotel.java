package ru.otus.hotelsbooker.model;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Hotel {

    private String name;
    private String city;
    private String country;
    private double rating;
    private String address;
}
