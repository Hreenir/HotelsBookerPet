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
import ru.otus.hotelsbooker.repository.HotelRepository;
import ru.otus.hotelsbooker.repository.LocalRoomRepository;
import ru.otus.hotelsbooker.repository.RoomRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@Getter
@Transactional
@RequiredArgsConstructor
public class RoomService {
    private final LocalRoomRepository localRoomRepository;
    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;

    public RoomDto addRoom(RoomDto roomDto) {
        Hotel hotel = hotelRepository.findAllById(roomDto.getHotelId());
        if (hotel == null) {
            throw new ResourceNotFoundException("Hotel with id=" + roomDto.getHotelId() + " not found!");
        }
        Room room = RoomMapper.mapToRoom(roomDto);
        room.setHotel(hotel);
        roomRepository.save(room);
        if (hotel.getRooms() == null) {
            hotel.setRooms(new ArrayList<>());
        }
        hotel.getRooms().add(room);
        RoomDto savedRoomDto = RoomMapper.mapToRoomDto(room);
        savedRoomDto.setHotelId(roomDto.getHotelId());
        return savedRoomDto;
    }

    public LocalRoomDto disableLocalRoom(long localRoomId) {
        localRoomRepository.disableLocalRoom(localRoomId);
        LocalRoom localRoom = localRoomRepository.findLocalRoomById(localRoomId);
        if (localRoom == null) {
            throw new ResourceNotFoundException("LocalRoom with id=" + localRoomId + " not found!");
        }
        return LocalRoomMapper.mapToLocalRoomDto(localRoom);
    }
    public LocalRoomDto addLocalRoom(LocalRoomDto localRoomDto){
        Room room = roomRepository.findRoomById(localRoomDto.getRoomId());
        if (room == null) {
            throw new ResourceNotFoundException("Room with id=" + localRoomDto.getRoomId() + " not found!");
        }

        LocalRoom localRoom = LocalRoomMapper.mapToLocalRoom(localRoomDto);
        localRoom.setRoom(room);

        LocalRoom savedLocalRoom = localRoomRepository.save(localRoom);
        RoomDto roomDto = RoomMapper.mapToRoomDto(room);
        roomDto.setHotelId(room.getHotel().getId());
        return LocalRoomDto.builder()
                .id(savedLocalRoom.getId())
                .roomId(localRoomDto.getRoomId())
                .roomNumber(savedLocalRoom.getRoomNumber())
                .enabled(savedLocalRoom.isEnabled())
                .room(roomDto)
                .build();
    }
    public List<LocalRoomDto> getAllLocalRooms(long roomId){
        Room room = roomRepository.findRoomById(roomId);
        if (room == null){
            throw new ResourceNotFoundException("Room with id=" + roomId + " not found!");
        }
        List<LocalRoom> localRooms = room.getRooms();
        return localRooms.stream()
                .map(LocalRoomMapper::mapToLocalRoomDto)
                .toList();

    }
    public LocalRoom findLocalRoomById (long localRoomId){
        if(!localRoomRepository.existsById(localRoomId)){
            throw new ResourceNotFoundException(("LocalRoom with id=" + localRoomId + " not found!"));
        }
        return localRoomRepository.findLocalRoomById(localRoomId);
    }
}
