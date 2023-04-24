package ru.otus.hotelsbooker.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.transaction.Transactional;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.hotelsbooker.dto.HotelDto;
import ru.otus.hotelsbooker.dto.RoomDto;
import ru.otus.hotelsbooker.model.Hotel;
import ru.otus.hotelsbooker.model.Room;
import ru.otus.hotelsbooker.repository.HotelJpaRepository;
import ru.otus.hotelsbooker.repository.RoomJpaRepository;

/**
 * Сервис для управления отелями: позволяет создавать, получать данные отеля, искать свободные
 * комнаты
 */
@Service
@Getter
@Transactional
public class HotelService {

    private final static double DEFAULT_RATING_FOR_NEW_HOTEL = 8.0;
    private final HotelJpaRepository hotelRepository;
    private final RoomJpaRepository roomJpaRepository;

    @Autowired
    public HotelService(HotelJpaRepository hotelRepository, RoomJpaRepository roomJpaRepository) {
        this.hotelRepository = hotelRepository;
        this.roomJpaRepository = roomJpaRepository;
    }


    public List<Room> findFreeRooms(Hotel hotel, LocalDate arrivalDate, LocalDate departureDate) {
        // поиск свободных номер по датам
        return null;
    }

    public List<HotelDto> findAll(String city) {
        List<Hotel> hotels = city == null ? hotelRepository.findAll() : hotelRepository.findAllByCityIgnoreCase(city);
        return hotels.stream()
                .map(hotel -> HotelMapper.mapToDto(hotel))
                .toList();
    }


    public HotelDto getHotelById(long id) {
        Hotel hotel = hotelRepository.findAllById(id);
        return HotelMapper.mapToDto(hotel);
    }

    // DTO->BL (мы пишем)->DTO
    // createHotel -> createNewHotel
    // createHotel -> newHotel
    // createHotel -> createHotel

    public HotelDto createNewHotel(HotelDto hotelDto) {

        Hotel hotel = Hotel.builder()
                .name(hotelDto.getName())
                .address(hotelDto.getAddress())
                .country(hotelDto.getCountry())
                .city(hotelDto.getCity())
                .rating(DEFAULT_RATING_FOR_NEW_HOTEL)
                .rooms(new ArrayList<>())
                .build();

        Hotel createdHotel = hotelRepository.save(hotel);


        return HotelMapper.mapToDto(createdHotel);
    }
    public void deleteHotel(Long id) {
        hotelRepository.deleteById(id);
    }

    public HotelDto updateHotel(HotelDto hotelDto) {
        // но сначала написать для тест (TDD)
        // реализовать логики поиска отделя, обновление данных и сохранения
        // только потом подключить контролер
        return null;
    }

    public RoomDto addRoom(RoomDto roomDto, Long id) {

        Hotel hotel = hotelRepository.findAllById(id);
        Room room = RoomMapper.mapToRoom(roomDto);
        room.setHotel(hotel);
        roomJpaRepository.save(room);
        hotel.getRooms().add(room);
        return RoomMapper.mapToRoomDto(room);

    }
}
