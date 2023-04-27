package ru.otus.hotelsbooker.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.otus.hotelsbooker.dto.HotelDto;
import ru.otus.hotelsbooker.dto.LocalRoomDto;
import ru.otus.hotelsbooker.dto.RoomDto;
import ru.otus.hotelsbooker.repository.LocalRoomJpaRepository;
import ru.otus.hotelsbooker.service.HotelService;
import ru.otus.hotelsbooker.service.RoomService;

@RestController
@RequestMapping("/hotel")
public class HotelsController {

    @Autowired
    private HotelService hotelsService;
    @Autowired
    private RoomService roomService;


    /**
     * GET localhost:8881/hotel?city=something
     *
     * @param city
     * @return
     */
    // todo: change return type to HotelDto
    @GetMapping
    public List<HotelDto> getAllHotels(@RequestParam(name = "city", required = false) String city) {
        return hotelsService.findAll(city);
    }

    /**
     * GET localhost:8881/hotel/{id}
     * <p>
     * GET localhost:8881/hotel/1
     *
     * @return
     */
    @GetMapping("/{id}")
    public HotelDto getHotelById(@PathVariable Long id) {
        return hotelsService.getHotelById(id);
    }


    /**
     * POST localhost:8881/hotel/{id}/room
     * * body {}
     *
     * @param roomDto
     * @param id
     * @return
     */
    @PostMapping(path = "/{id}/room", consumes = "application/json")
    public RoomDto addRoom(@RequestBody RoomDto roomDto, @PathVariable Long id) {
        return hotelsService.addRoom(roomDto, id);
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

    /**
     * POST localhost:8881/hotel
     * body {}
     *
     * @param hotel
     * @return
     */
    @PostMapping(consumes = "application/json", produces = "application/json")
    public HotelDto createHotel(@RequestBody HotelDto hotel) {
        return hotelsService.createNewHotel(hotel);
    }

    /**
     * PuT localhost:8881/hotel/{id}
     * body {}
     *
     * @param hotel
     * @return
     */

    @PutMapping(consumes = "application/json", produces = "application/json")
    public HotelDto updateHotel(@PathVariable Long id, @RequestBody HotelDto hotel) {
        return hotelsService.updateHotel(id, hotel);
    }

    @PostMapping(path = "/{id}/localroom", consumes = "application/json",produces = "application/json")
    public LocalRoomDto addLocalRoom(@RequestBody LocalRoomDto localRoomDto, @PathVariable Long id){
        return roomService.addLocalRoom(localRoomDto,id);
    }
}
