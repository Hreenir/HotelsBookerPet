package ru.otus.hotelsbooker.service;

import org.springframework.stereotype.Service;
import ru.otus.hotelsbooker.model.Hotel;

import java.util.List;

@Service
public class HotelsService {

    public List<Hotel> getAllHotels() {
        Hotel hotel1 = new Hotel("Весна", "Сочи", "Россия", 2012);
        Hotel hotel2 = new Hotel("Свобода", "Москва", "Россия", 2000);

        return List.of(hotel1, hotel2);
    }
}
