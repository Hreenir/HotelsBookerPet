package ru.otus.hotelsbooker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.dto.LocalRoomDto;
import ru.otus.dto.RoomDto;
import ru.otus.hotelsbooker.service.HotelNotFoundException;
import ru.otus.hotelsbooker.service.RoomService;

import java.util.Optional;

@RestController
@RequestMapping("/room")
public class RoomsController {
    @Autowired
    private RoomService roomService;

    /**
     * POST localhost:8881/room/hotel/{id}
     * * body {}
     *
     * @param roomDto
     * @param id
     * @return
     */
    @PostMapping(path = "/hotel/{id}", consumes = "application/json")
    public ResponseEntity addRoom(@RequestBody RoomDto roomDto, @PathVariable Long id) {
        try {
            RoomDto result = roomService.addRoom(roomDto, id);
            return ResponseEntity.of(Optional.of(result));
        } catch (HotelNotFoundException e) {
            return ResponseEntity.of(Optional.of(e.getMessage()));
        }
    }

    /**
     * POST localhost:8881/room/{id}/localroom
     * body {}
     *
     * @param localRoomDto
     * @return
     */
    @PostMapping(path = "/{id}/localroom", consumes = "application/json", produces = "application/json")
    public LocalRoomDto addLocalRoom(@RequestBody LocalRoomDto localRoomDto, @PathVariable Long id) {
        return roomService.addLocalRoom(localRoomDto, id);
    }

    /**
     * PUT localhost:8881/room/localroom/{id}
     *
     * @param id
     */

    @PutMapping(path = "/localroom/{id}")
    public void disableLocalRoom(@PathVariable Long id) {
        roomService.disableLocalRoom(id);
    }
}
