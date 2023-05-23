package ru.otus.hotelsbooker.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.dto.HotelDto;
import ru.otus.hotelsbooker.exception.HotelNotFoundException;
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
    public ResponseEntity getAllHotels(@RequestParam(name = "city", required = false) String city) {
        return ResponseEntity.ok(hotelsService.findAll(city));
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
    public ResponseEntity getHotelById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(hotelsService.getHotelById(id));
        } catch (HotelNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


    /**
     * POST localhost:8881/hotel
     * * body {}
     *
     * @return
     */
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity createHotel(@RequestBody HotelDto hotel) {
        return ResponseEntity.ok(hotelsService.createNewHotel(hotel));
    }

    /**
     * PuT localhost:8881/hotel/{id}
     * body {}
     *
     * @param hotel
     * @return
     */
    @PutMapping(path = "/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity updateHotel(@PathVariable Long id, @RequestBody HotelDto hotel) {
        try {
            return ResponseEntity.ok(hotelsService.updateHotel(id, hotel));
        } catch (HotelNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
