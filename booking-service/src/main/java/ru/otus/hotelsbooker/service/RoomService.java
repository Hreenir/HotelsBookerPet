package ru.otus.hotelsbooker.service;

import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.dto.LocalRoomDto;
import ru.otus.dto.RoomDto;
import ru.otus.hotelsbooker.exception.ResourceNotFoundException;
import ru.otus.hotelsbooker.mapper.LocalRoomMapper;
import ru.otus.hotelsbooker.mapper.RoomMapper;
import ru.otus.hotelsbooker.model.Hotel;
import ru.otus.hotelsbooker.model.LocalRoom;
import ru.otus.hotelsbooker.model.Room;
import ru.otus.hotelsbooker.repository.LocalRoomRepository;
import ru.otus.hotelsbooker.repository.RoomRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@Getter
@RequiredArgsConstructor
public class RoomService {
    private final LocalRoomRepository localRoomRepository;
    private final RoomRepository roomRepository;
    private final HotelService hotelService;
    @Transactional
    public Room addRoom(RoomDto roomDto) {
        Hotel hotel = hotelService.getHotelById(roomDto.getHotelId());
        Room room = RoomMapper.mapToRoom(roomDto);
        room.setHotel(hotel);
        roomRepository.save(room);
        if (hotel.getRooms() == null) {
            hotel.setRooms(new ArrayList<>());
        }
        hotel.getRooms().add(room);
        return room;
    }

    @Transactional
    public LocalRoom disableLocalRoom(LocalRoomDto localRoomDto) {
        findLocalRoomById(localRoomDto.getId());
        localRoomRepository.disableLocalRoom(localRoomDto.getId());
        return findLocalRoomById(localRoomDto.getId());
    }

    @Transactional
    public LocalRoom addLocalRoom(LocalRoomDto localRoomDto){
        Room room = findRoomById(localRoomDto.getRoomId());
        LocalRoom localRoom = LocalRoomMapper.mapToLocalRoom(localRoomDto);
        localRoom.setRoom(room);
        return localRoomRepository.save(localRoom);
    }
    public List<LocalRoom> getAllLocalRooms(){
        return localRoomRepository.findAll();
    }

    public LocalRoom findLocalRoomById (long localRoomId){
        if(!localRoomRepository.existsById(localRoomId)){
            throw new ResourceNotFoundException(("LocalRoom with id=" + localRoomId + " not found!"));
        }
        return localRoomRepository.findLocalRoomById(localRoomId);
    }

    public Room findRoomById (long roomId){
        if (!roomRepository.existsById(roomId)) {
            throw new ResourceNotFoundException("Room with id=" + roomId + " not found!");
        }
        return roomRepository.findRoomById(roomId);
    }
}
