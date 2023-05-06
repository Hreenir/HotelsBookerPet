package ru.otus.hotelsbooker.service;

import jakarta.transaction.Transactional;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.dto.LocalRoomDto;
import ru.otus.dto.RoomDto;
import ru.otus.hotelsbooker.mapper.LocalRoomMapper;
import ru.otus.hotelsbooker.mapper.RoomMapper;
import ru.otus.hotelsbooker.model.Hotel;
import ru.otus.hotelsbooker.model.LocalRoom;
import ru.otus.hotelsbooker.model.Room;
import ru.otus.hotelsbooker.repository.HotelJpaRepository;
import ru.otus.hotelsbooker.repository.LocalRoomJpaRepository;
import ru.otus.hotelsbooker.repository.RoomJpaRepository;

import java.util.ArrayList;

@Service
@Getter
@Transactional
public class RoomService {
    private LocalRoomJpaRepository localRoomJpaRepository;
    private RoomJpaRepository roomJpaRepository;
    private HotelJpaRepository hotelRepository;

    @Autowired
    public RoomService(LocalRoomJpaRepository localRoomJpaRepository, RoomJpaRepository roomJpaRepository, HotelJpaRepository hotelRepository) {
        this.localRoomJpaRepository = localRoomJpaRepository;
        this.roomJpaRepository = roomJpaRepository;
        this.hotelRepository = hotelRepository;
    }

    public RoomDto addRoom(RoomDto roomDto, long hotelId) {
        Hotel hotel = hotelRepository.findAllById(hotelId);
        if (hotel == null) {
            throw new HotelNotFoundException("Hotel with id=" + hotelId + " not found!");
        }
        Room room = RoomMapper.mapToRoom(roomDto);
        room.setHotel(hotel);
        roomJpaRepository.save(room);
        if (hotel.getRooms() == null) {
            hotel.setRooms(new ArrayList<>());
        }
        hotel.getRooms().add(room);
        return RoomMapper.mapToRoomDto(room);
    }

    public void disableLocalRoom(long localRoomId) {
        localRoomJpaRepository.disableLocalRoom(localRoomId);
    }
    public LocalRoomDto addLocalRoom(LocalRoomDto localRoomDto, long roomId){
        Room room = roomJpaRepository.findRoomById(roomId);

        LocalRoom localRoom = LocalRoomMapper.mapToLocalRoom(localRoomDto);
        localRoom.setRoom(room);

        LocalRoom savedLocalRoom = localRoomJpaRepository.save(localRoom);
        RoomDto roomDto = RoomMapper.mapToRoomDto(room);
        return LocalRoomDto.builder()
                .id(savedLocalRoom.getId())
                .roomNumber(savedLocalRoom.getRoomNumber())
                .enabled(savedLocalRoom.isEnabled())
                .room(roomDto)
                .build();
    }

}
