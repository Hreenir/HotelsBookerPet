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
import ru.otus.hotelsbooker.mapper.HotelMapper;
import ru.otus.hotelsbooker.model.Hotel;
import ru.otus.hotelsbooker.model.Room;
import ru.otus.hotelsbooker.repository.HotelJpaRepository;
import ru.otus.hotelsbooker.repository.LocalRoomJpaRepository;
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
    private final LocalRoomJpaRepository localRoomJpaRepository;
    private final RoomService roomService;

    @Autowired
    public HotelService(HotelJpaRepository hotelRepository, RoomJpaRepository roomJpaRepository, LocalRoomJpaRepository localRoomJpaRepository, RoomService roomService) {
        this.hotelRepository = hotelRepository;
        this.roomJpaRepository = roomJpaRepository;
        this.localRoomJpaRepository = localRoomJpaRepository;
        this.roomService = roomService;
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

    public HotelDto updateHotel(Long id, HotelDto hotelDto) {

        Hotel hotel = hotelRepository.findAllById(id);

        if (hotelDto.getName() != null) {
            hotel.setName(hotelDto.getName());
        }
        if (hotelDto.getCity() != null) {
            hotel.setCity(hotelDto.getCity());
        }
        if (hotelDto.getCountry() != null) {
            hotel.setCountry(hotelDto.getCountry());
        }
        if (hotelDto.getAddress() != null) {
            hotel.setAddress(hotelDto.getAddress());
        }

        Hotel updatedHotel = hotelRepository.save(hotel);

        return HotelMapper.mapToDto(updatedHotel);

    }

    public RoomDto addRoom(RoomDto roomDto, Long id) {
        Hotel hotel = hotelRepository.findAllById(id);
        roomService.addRoom(roomDto, hotel);
        return roomDto;
    }

}
