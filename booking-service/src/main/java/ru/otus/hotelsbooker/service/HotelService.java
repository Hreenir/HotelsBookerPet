package ru.otus.hotelsbooker.service;

import java.lang.module.ResolutionException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
    private final RoomRepository roomRepository;
    private final LocalRoomRepository localRoomRepository;
    private final RoomService roomService;

    public List<Room> findFreeRooms(Hotel hotel, LocalDate arrivalDate, LocalDate departureDate) {
        // поиск свободных номер по датам
        return null;
    }


    public List<Hotel> findAll(SearchDto searchDto) {
        return searchDto.getCity() == null ?
                hotelRepository.findAll() : hotelRepository.findAllByCityIgnoreCase(searchDto.getCity());
    }


    public HotelDto getHotelById(long id) {
        Hotel hotel = hotelRepository.findAllById(id);
        if (hotel == null) {
            throw new ResourceNotFoundException("Hotel with id=" + id + " not found!");
        }
        return HotelMapper.mapToDto(hotel);
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
        Hotel hotel = hotelRepository.findAllById(hotelDto.getId());
        if (hotel == null) {
            throw new ResourceNotFoundException("Hotel with id=" + hotelDto.getId() + " not found!");
        }
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
        return hotelRepository.save(hotel);

    }
    @Transactional
    public RoomDto addRoom(RoomDto roomDto, Long id) {
        Hotel hotel = hotelRepository.findAllById(id);
        if (hotel == null) {
            throw new ResourceNotFoundException("Hotel with id=" + id + " not found!");
        }
        Room room = RoomMapper.mapToRoom(roomDto);
        room.setHotel(hotel);
        roomRepository.save(room);
        if (hotel.getRooms() == null) {
            hotel.setRooms(new ArrayList<>());
        }
        hotel.getRooms().add(room);
        return RoomMapper.mapToRoomDto(room);

    }
    @Transactional
    public void disableLocalRoom(long localRoomId){
        if (localRoomRepository.findLocalRoomById(localRoomId) != null) {
            localRoomRepository.disableLocalRoom(localRoomId);
        }
    }

    public void clearAll() {
        List<Hotel> list = hotelRepository.findAll();
        list.forEach(hotelRepository::delete);
    }
}
