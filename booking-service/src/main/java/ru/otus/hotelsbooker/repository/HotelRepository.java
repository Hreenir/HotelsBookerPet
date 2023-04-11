package ru.otus.hotelsbooker.repository;

import org.springframework.stereotype.Repository;
import ru.otus.hotelsbooker.model.Hotel;

import java.util.List;
import java.util.stream.Collectors;
@Repository
public class HotelRepository {

    private final List<Hotel> allHotels = List.of(
            new Hotel("Hilton", "Москва", "Россия", 9.6, "Красная площать д.1"),
            new Hotel("Hilton", "Нижний Новгород", "Россия", 9.6, "Красная площать д.1"),
            new Hotel("Hilton", "Москва", "Россия", 9.6, "Красная площать д.1"),
            new Hotel("Hilton", "Санкт-Петербург", "Россия", 9.6, "Красная площать д.1"));

    public List<Hotel> findAll(String city) {
        if (city == null){
            return allHotels;
        }
        return allHotels.stream()
                .filter(hotel -> hotel.getCity().toLowerCase().equals(city.toLowerCase()))
                .collect(Collectors.toList());
    }
}
