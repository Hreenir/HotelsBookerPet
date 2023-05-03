package ru.otus.hotelsbooker.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.otus.dto.HotelDto;
import ru.otus.hotelsbooker.service.HotelService;

@RestController
@RequestMapping("/hotel")
public class HotelsController {

    @Autowired
    private HotelService hotelsService;

    /**
     * GET localhost:8881/hotel?city=something
     *
     * @param city
     * @return
     */
    @GetMapping
    public List<HotelDto> getAllHotels(@RequestParam(name = "city", required = false) String city) {
        return hotelsService.findAll(city);
    }

    /**
     * GET localhost:8881/hotel/{id}
     * <p>
     * GET localhost:8881/hotel/1
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public HotelDto getHotelById(@PathVariable Long id) {
        return hotelsService.getHotelById(id);
    }


    /**
     * POST localhost:8881/hotel
     * * body {}
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
    @PutMapping(path = "/{id}", consumes = "application/json", produces = "application/json")
    public HotelDto updateHotel(@PathVariable Long id, @RequestBody HotelDto hotel) {
        return hotelsService.updateHotel(id, hotel);
    }
}
