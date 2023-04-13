package ru.otus.hotelsbooker.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.hotelsbooker.dto.HotelDto;
import ru.otus.hotelsbooker.model.Hotel;
import ru.otus.hotelsbooker.repository.HotelJpaRepository;
import ru.otus.hotelsbooker.repository.HotelMapRepository;
import ru.otus.hotelsbooker.model.Room;

/**
 *
 */
@Service
public class HotelService {
    private final HotelJpaRepository hotelRepository;
    @Autowired
    public HotelService(HotelJpaRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    public List<Room> findFreeRooms(Hotel hotel, LocalDate arrivalDate, LocalDate departureDate) {
        // поиск свободных номер по датам
        return null;
    }

    public List<Hotel> findAll(String city) {
        return city == null ? hotelRepository.findAll() : hotelRepository.findAllByCityIgnoreCase(city);
    }

    public Long save(HotelDto hotelDto) {
        Hotel hotel = Hotel.builder()
                .name(hotelDto.getName())
                .address(hotelDto.getAddress())
                .country(hotelDto.getCountry())
                .city(hotelDto.getCity())
                .rating(8.0)
                .build();
        Hotel saved = hotelRepository.save(hotel);
        return saved.getId();
    }
}
