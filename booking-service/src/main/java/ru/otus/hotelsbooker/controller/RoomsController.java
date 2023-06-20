package ru.otus.hotelsbooker.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.otus.dto.LocalRoomDto;
import ru.otus.dto.RoomDto;
import ru.otus.hotelsbooker.mapper.LocalRoomMapper;
import ru.otus.hotelsbooker.mapper.RoomMapper;
import ru.otus.hotelsbooker.service.RoomService;

import java.util.List;

@RestController
@RequestMapping("api/v1/rooms")
@RequiredArgsConstructor
public class RoomsController {
    private final RoomService roomService;

    @PostMapping
    public RoomDto addRoom(@RequestBody RoomDto roomDto) {
        return RoomMapper.mapToRoomDto(roomService.addRoom(roomDto));
    }

    @PostMapping(path = "/localroom")
    public LocalRoomDto addLocalRoom(@RequestBody LocalRoomDto localRoomDto) {
        return LocalRoomMapper.mapToLocalRoomDto(roomService.addLocalRoom(localRoomDto));
    }

    @PatchMapping(path = "/localroom/disable")
    public LocalRoomDto disableLocalRoom(@RequestBody LocalRoomDto localRoomDto) {
        return LocalRoomMapper.mapToLocalRoomDto(roomService.disableLocalRoom(localRoomDto));
    }

    @GetMapping(path = "/localroom")
    public List<LocalRoomDto> getAllLocalRooms() {
        return roomService.getAllLocalRooms().stream()
                .map(LocalRoomMapper::mapToLocalRoomDto)
                .toList();
    }
}
