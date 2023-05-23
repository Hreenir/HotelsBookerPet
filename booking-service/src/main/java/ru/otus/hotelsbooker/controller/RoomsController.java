package ru.otus.hotelsbooker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.dto.LocalRoomDto;
import ru.otus.dto.RoomDto;
import ru.otus.hotelsbooker.exception.HotelNotFoundException;
import ru.otus.hotelsbooker.exception.LocalRoomNotFoundException;
import ru.otus.hotelsbooker.exception.HotelNotFoundException;
import ru.otus.hotelsbooker.exception.LocalRoomNotFoundException;
import ru.otus.hotelsbooker.exception.RoomNotFoundException;
import ru.otus.hotelsbooker.service.RoomService;

import java.util.List;
import java.util.Locale;
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
    public ResponseEntity disableLocalRoom(@PathVariable Long localRoomId) {
        try {
            LocalRoomDto result = roomService.disableLocalRoom(localRoomId);
            return ResponseEntity.of(Optional.of(result));
        } catch (LocalRoomNotFoundException e) {
            return ResponseEntity.of(Optional.of(e.getMessage()));
        }
    }

    @GetMapping(path = "/{roomId}/localroom", produces = "application/json")
    public ResponseEntity getAllLocalRooms (@PathVariable Long roomId) {
        try {
            List<LocalRoomDto> result = roomService.getAllLocalRooms(roomId);
            return ResponseEntity.ok(result);
        } catch (LocalRoomNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
