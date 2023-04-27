package ru.otus.hotelsbooker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.otus.hotelsbooker.dto.LocalRoomDto;
import ru.otus.hotelsbooker.dto.RoomDto;
import ru.otus.hotelsbooker.service.RoomService;

@RestController
@RequestMapping("/room")
public class RoomsController {
    @Autowired
    private RoomService roomService;

    /**
     * POST localhost:8881/hotel/{id}/room
     * * body {}
     *
     * @param roomDto
     * @param id
     * @return
     */
    @PostMapping(path = "/{id}", consumes = "application/json")
    public RoomDto addRoom(@RequestBody RoomDto roomDto, @PathVariable Long id) {
        return roomService.addRoom(roomDto, id);
    }

    /**
     * POST localhost:8881/{id}/localroom
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
     * PUT localhost:8881/hotel/localroom/{id}
     *
     * @param id
     */

    @PutMapping(path = "/localroom/{id}")
    public void disableLocalRoom(@PathVariable Long id) {
        roomService.disableLocalRoom(id);
    }


}
