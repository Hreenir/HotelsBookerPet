package ru.otus.hotelsbooker.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.hotelsbooker.model.BookingCase;
import ru.otus.hotelsbooker.model.Hotel;
import ru.otus.hotelsbooker.repository.HotelRepository;
import ru.otus.hotelsbooker.model.Room;

/**
 * Искать варианты бронирования по запросу пользователя
 */
@Service
public class BookingSearchService {
    private HotelService hotelService;

    @Autowired
    public BookingSearchService(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    public List<BookingCase> search(String city, LocalDate arrivalDate, LocalDate departureDate) {
        // Получить все отели в городе
        HotelRepository hotelRepository = new HotelRepository();
        List<Hotel> hotels = hotelRepository.findAll(city);

        // у отелей получить свободные на указанные даты номера
        List<Room> rooms = new ArrayList<>();

        for (Hotel hotel : hotels) {
            List<Room> freeRooms = hotelService.findFreeRooms(hotel, arrivalDate, departureDate);
            rooms.addAll(freeRooms);
        }

        // полученный список отсортировать по рейтингу отеля

        // из списка номеров сделать список вариантов бронирования

        return null;
    }
}
