package ru.otus.hotelsbooker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.dto.LocalRoomDto;
import ru.otus.dto.RoomDto;
import ru.otus.hotelsbooker.service.HotelNotFoundException;
import ru.otus.hotelsbooker.service.RoomNotFoundException;
import ru.otus.hotelsbooker.service.RoomService;

import java.util.Optional;

@RestController
@RequestMapping("/hotel/{hotelId}/room")
public class RoomsController {
    @Autowired
    private RoomService roomService;

    /**
     * POST localhost:8881/hotel/{hotelId}/room
     * * body {}
     *
     * @param roomDto
     * @param hotelId
     * @return
     */
    @PostMapping(path = "", consumes = "application/json")
    public ResponseEntity addRoom(@RequestBody RoomDto roomDto, @PathVariable Long hotelId) {
        try {
            RoomDto result = roomService.addRoom(roomDto, hotelId);
            return ResponseEntity.of(Optional.of(result));
        } catch (HotelNotFoundException e) {
            return ResponseEntity.of(Optional.of(e.getMessage()));
        }
    }

    /**
     * POST localhost:8881/hotel/{hotelId}/room/{roomId}/localroom
     * body {}
     *
     * @param localRoomDto
     * @return
     */
    @PostMapping(path = "/{roomId}/localroom", consumes = "application/json", produces = "application/json")
    public ResponseEntity addLocalRoom(@RequestBody LocalRoomDto localRoomDto, @PathVariable Long roomId) {
        try {
            LocalRoomDto result = roomService.addLocalRoom(localRoomDto, roomId);
            return ResponseEntity.of(Optional.of(result));
        } catch (RoomNotFoundException e) {
            return ResponseEntity.of(Optional.of(e.getMessage()));
        }
    }

    /**
     * PUT localhost:8881/hotel/{hotelId}/room/{id}/localroom
     *
     * @param localRoomId
     */

    @PutMapping(path = "/{roomId}/localroom/{localRoomId}/disable")
    public void disableLocalRoom(@PathVariable Long localRoomId) {
        roomService.disableLocalRoom(localRoomId);
    }
}
