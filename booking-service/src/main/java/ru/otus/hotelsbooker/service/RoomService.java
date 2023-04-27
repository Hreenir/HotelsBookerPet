package ru.otus.hotelsbooker.service;

import jakarta.transaction.Transactional;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.service.annotation.GetExchange;
import ru.otus.hotelsbooker.dto.LocalRoomDto;
import ru.otus.hotelsbooker.dto.RoomDto;
import ru.otus.hotelsbooker.model.Hotel;
import ru.otus.hotelsbooker.model.LocalRoom;
import ru.otus.hotelsbooker.model.Room;
import ru.otus.hotelsbooker.repository.LocalRoomJpaRepository;
import ru.otus.hotelsbooker.repository.RoomJpaRepository;

@Service
@Getter
@Transactional
public class RoomService {
    private LocalRoomJpaRepository localRoomJpaRepository;
    private RoomJpaRepository roomJpaRepository;

    @Autowired
    public RoomService(LocalRoomJpaRepository localRoomJpaRepository, RoomJpaRepository roomJpaRepository) {
        this.localRoomJpaRepository = localRoomJpaRepository;
        this.roomJpaRepository = roomJpaRepository;
    }

    public RoomDto addRoom(RoomDto roomDto, Hotel hotel) {
        Room room = RoomMapper.mapToRoom(roomDto);
        room.setHotel(hotel);
        roomJpaRepository.save(room);
        hotel.getRooms().add(room);
        return RoomMapper.mapToRoomDto(room);
    }

    public void disableLocalRoom(long localRoomId) {
        localRoomJpaRepository.disableLocalRoom(localRoomId);
    }
    public LocalRoomDto addLocalRoom(LocalRoomDto localRoomDto, long roomId){
        Room room = roomJpaRepository.findAllById(roomId);
        LocalRoom localRoom = LocalRoom.builder()
                .roomNumber(localRoomDto.getRoomNumber())
                .enabled(localRoomDto.isEnabled())
                .room(room)
                .build();
        LocalRoom savedLocalRoom = localRoomJpaRepository.save(localRoom);
        return LocalRoomDto.builder()
                .id(savedLocalRoom.getId())
                .roomNumber(savedLocalRoom.getRoomNumber())
                .enabled(savedLocalRoom.isEnabled())
                .room(savedLocalRoom.getRoom())
                .build();

    }
}
