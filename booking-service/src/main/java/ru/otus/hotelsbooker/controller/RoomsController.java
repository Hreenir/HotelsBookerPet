package ru.otus.hotelsbooker.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.otus.dto.LocalRoomDto;
import ru.otus.dto.RoomDto;
import ru.otus.hotelsbooker.service.RoomService;

import java.util.List;

@RestController
@RequestMapping("api/v1/rooms")
@RequiredArgsConstructor
public class RoomsController {
    private final RoomService roomService;

    @PostMapping(consumes = "application/json")
    public RoomDto addRoom(@RequestBody RoomDto roomDto) {
        return roomService.addRoom(roomDto);
    }

    @PostMapping(path = "/localroom", consumes = "application/json", produces = "application/json")
    public LocalRoomDto addLocalRoom(@RequestBody LocalRoomDto localRoomDto) {
        return roomService.addLocalRoom(localRoomDto);
    }

    @PutMapping(path = "/{roomId}/localroom/{localRoomId}/disable")
    public LocalRoomDto disableLocalRoom(@PathVariable Long localRoomId) {
        return roomService.disableLocalRoom(localRoomId);
    }

    @GetMapping(path = "/{roomId}/localroom", produces = "application/json")
    public List<LocalRoomDto> getAllLocalRooms(@PathVariable Long roomId) {
        return roomService.getAllLocalRooms(roomId);
    }
}
