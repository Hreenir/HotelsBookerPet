package ru.otus.hotelsbooker.repository;

import org.springframework.stereotype.Repository;
import ru.otus.hotelsbooker.model.Hotel;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@Repository
@Deprecated
public class HotelMapRepository implements HotelRepository {

   // private final List<Hotel> allHotels = List.of(
   //         new Hotel(1L, "Hilton", "Москва", "Россия", 9.6, "Красная площать д.1"),
   //         new Hotel(2L, "Hilton", "Нижний Новгород", "Россия", 9.6, "Красная площать д.1"),
   //         new Hotel(3L, "Hilton", "Москва", "Россия", 9.6, "Красная площать д.1"),
   //         new Hotel(4L, "Hilton", "Санкт-Петербург", "Россия", 9.6, "Красная площать д.1"));
    private final List<Hotel> allHotels = new ArrayList<>();
    public List<Hotel> findAllByCityIgnoreCase(String city) {
        if (city == null){
            return allHotels;
        }
        return allHotels.stream()
                .filter(hotel -> hotel.getCity().toLowerCase().equals(city.toLowerCase()))
                .collect(Collectors.toList());
    }
}
