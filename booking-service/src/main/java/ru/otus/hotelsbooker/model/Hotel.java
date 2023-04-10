package ru.otus.hotelsbooker.model;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
public class Hotel {

    private String name;
    private String city;
    private String country;
    private int foundationYear;

}
