package ru.otus.hotelsbooker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.dto.LocalRoomDto;
import ru.otus.dto.RoomDto;
import ru.otus.hotelsbooker.exception.HotelNotFoundException;
import ru.otus.hotelsbooker.exception.LocalRoomNotFoundException;
import ru.otus.hotelsbooker.exception.RoomNotFoundException;
import ru.otus.hotelsbooker.service.RoomService;

import java.util.List;
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
            return ResponseEntity.ok(roomService.addRoom(roomDto, hotelId));
        } catch (HotelNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
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
            return ResponseEntity.ok(roomService.addLocalRoom(localRoomDto, roomId));
        } catch (RoomNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    /**
     * PUT localhost:8881/hotel/{hotelId}/room/{id}/localroom
     *
     * @param localRoomId
     */

    @PutMapping(path = "/{roomId}/localroom/{localRoomId}/disable")
    public ResponseEntity disableLocalRoom(@PathVariable Long localRoomId) {
        try {
            return ResponseEntity.ok(roomService.disableLocalRoom(localRoomId));
        } catch (LocalRoomNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping(path = "/{roomId}/localroom", produces = "application/json")
    public ResponseEntity getAllLocalRooms (@PathVariable Long roomId) {
        try {
            return ResponseEntity.ok(roomService.getAllLocalRooms(roomId));
        } catch (RoomNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
