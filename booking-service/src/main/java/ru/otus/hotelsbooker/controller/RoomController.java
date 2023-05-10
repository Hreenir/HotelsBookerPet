package ru.otus.hotelsbooker.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.otus.dto.RoomDto;
import ru.otus.hotelsbooker.service.RoomService;

@RestController
@RequestMapping("/room")
public class RoomController {
    @Autowired
    private RoomService roomService;

    /**
     * GET localhost:8881/room?hotelId=1
     *
     * @param hotelId
     * @return
     */
    @GetMapping
    public List<RoomDto> getAllRooms(@RequestParam(name = "hotelId") Long hotelId) {
        return roomService.getHotelById(hotelId);
    }

    /**
     * GET localhost:8881/room/{id}
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public RoomDto getRoomById(@PathVariable Long id) {
        return roomService.getRoomById(id);
    }

    /**
     * POST localhost:8881/room
     * body {}
     *
     * @param room
     * @return
     */
    @PostMapping(consumes = "application/json", produces = "application/json")
    public RoomDto createNewRoom(@RequestBody RoomDto room) {
        return roomService.createNewRoom(room);
    }

    /**
     * PUT localhost:8881/room/{id}
     * body {}
     *
     * @param id
     * @param room
     * @return
     */
    @PutMapping(path = "/{id}", consumes = "application/json", produces = "application/json")
    public RoomDto updateRoom(@PathVariable Long id, @RequestBody RoomDto room) {
        return roomService.updateRoom(id, room);
    }
}