package ru.otus.hotelsbooker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.hotelsbooker.model.Hotel;
import ru.otus.hotelsbooker.service.HotelsService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/hotel")
public class HotelsController {

    @Autowired
    HotelsService hotelsService;

    @GetMapping
    public List<Hotel> getAllHotels() {
        return hotelsService.getAllHotels();
    }
}
