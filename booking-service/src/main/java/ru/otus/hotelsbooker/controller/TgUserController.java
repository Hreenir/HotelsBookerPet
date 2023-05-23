package ru.otus.hotelsbooker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.dto.TgUserDto;
import ru.otus.hotelsbooker.exception.TgUserNotFoundException;
import ru.otus.hotelsbooker.service.TgUserService;

import java.util.Optional;

@RestController
@RequestMapping("/tguser")
public class TgUserController {
    @Autowired
    private TgUserService tgUsersService;

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity setRole(@RequestBody TgUserDto tgUserDto){
        return ResponseEntity.ok(tgUsersService.createTgUser(tgUserDto));
    }
    @GetMapping("/{id}")
    public ResponseEntity findTgUser(@PathVariable Long id){
        try {
            return ResponseEntity.ok(tgUsersService.getUserById(id));
        } catch (TgUserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
