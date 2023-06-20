package ru.otus.hotelsbooker.service;

import java.lang.module.ResolutionException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.dto.HotelDto;
import ru.otus.dto.RoomDto;
import ru.otus.dto.SearchDto;
import ru.otus.hotelsbooker.exception.ResourceNotFoundException;
import ru.otus.hotelsbooker.mapper.HotelMapper;
import ru.otus.hotelsbooker.mapper.RoomMapper;
import ru.otus.hotelsbooker.model.Hotel;
import ru.otus.hotelsbooker.model.Room;
import ru.otus.hotelsbooker.repository.HotelRepository;
import ru.otus.hotelsbooker.repository.LocalRoomRepository;
import ru.otus.hotelsbooker.repository.RoomRepository;

/**
 * Сервис для управления отелями: позволяет создавать, получать данные отеля, искать свободные
 * комнаты
 */
@Service
@Getter
@RequiredArgsConstructor
public class HotelService {
    private final static double DEFAULT_RATING_FOR_NEW_HOTEL = 8.0;
    private final HotelRepository hotelRepository;

    public List<Room> findFreeRooms(Hotel hotel, LocalDate arrivalDate, LocalDate departureDate) {
        // поиск свободных номер по датам
        return null;
    }

    public List<Hotel> findAll(SearchDto searchDto) {
        return searchDto.getCity() == null ?
                hotelRepository.findAll() : hotelRepository.findAllByCityIgnoreCase(searchDto.getCity());
    }

    public Hotel getHotelById(long id) {
        if (!hotelRepository.existsById(id)) {
            throw new ResourceNotFoundException("Hotel with id=" + id + " not found!");
        }
        return hotelRepository.findAllById(id);
    }

    @Transactional
    public Hotel createNewHotel(HotelDto hotelDto) {
        return hotelRepository.save(Hotel.builder()
                .name(hotelDto.getName())
                .address(hotelDto.getAddress())
                .country(hotelDto.getCountry())
                .city(hotelDto.getCity())
                .rating(DEFAULT_RATING_FOR_NEW_HOTEL)
                .rooms(new ArrayList<>())
                .build());
    }
    @Transactional
    public void deleteHotel(Long id) {
        hotelRepository.deleteById(id);
    }

    @Transactional
    public Hotel updateHotel(HotelDto hotelDto) {
        Hotel hotel = getHotelById(hotelDto.getId());
        hotel.setName(Optional.ofNullable(hotelDto.getName()).orElse(hotel.getName()));
        hotel.setCity(Optional.ofNullable(hotelDto.getCity()).orElse(hotel.getCity()));
        hotel.setCountry(Optional.ofNullable(hotelDto.getCountry()).orElse(hotel.getCountry()));
        hotel.setAddress(Optional.ofNullable(hotelDto.getAddress()).orElse(hotel.getAddress()));
        return hotelRepository.save(hotel);

    }

    public void clearAll() {
        List<Hotel> list = hotelRepository.findAll();
        list.forEach(hotelRepository::delete);
    }
}
