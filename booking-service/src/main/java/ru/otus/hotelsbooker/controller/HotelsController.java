package ru.otus.hotelsbooker.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.hotelsbooker.dto.HotelDto;
import ru.otus.hotelsbooker.model.Hotel;
import ru.otus.hotelsbooker.service.HotelService;

@RestController
@RequestMapping("/hotel")
public class HotelsController {

  @Autowired
  HotelService hotelsService;

  @GetMapping
  public List<Hotel> getAllHotels(@RequestParam(name = "city", required = false) String city) {
    return hotelsService.findAll(city);
  }

  @GetMapping("/{id}")
  public HotelDto getHotelById(@PathVariable Long id) {
    // TODO сделать реализацию
    return hotelsService.getHotelById(id);
  }

  @PostMapping
  public HotelDto createHotel(@RequestBody HotelDto hotel) {
    return hotelsService.createNewHotel(hotel);
  }
}
