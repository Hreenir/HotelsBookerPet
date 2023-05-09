package ru.otus.hotelsbooker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.dto.RoomDto;
import ru.otus.dto.TgUserDto;
import ru.otus.hotelsbooker.service.HotelNotFoundException;
import ru.otus.hotelsbooker.service.TgUserNotFoundException;
import ru.otus.hotelsbooker.service.TgUserService;

import java.util.Optional;

@RestController
@RequestMapping("/tguser")
public class TgUserController {
    @Autowired
    private TgUserService tgUsersService;

    @PostMapping(consumes = "application/json", produces = "application/json")
    public TgUserDto setRole(@RequestBody TgUserDto tgUserDto){
        return tgUsersService.createTgUser(tgUserDto);
    }
    @GetMapping("/{id}")
    public ResponseEntity findTgUser(@PathVariable Long id){
        try {
            TgUserDto result = tgUsersService.getUserById(id);
            return ResponseEntity.of(Optional.of(result));
        } catch (TgUserNotFoundException e) {
            return ResponseEntity.of(Optional.of(e.getMessage()));
        }
    }

}
