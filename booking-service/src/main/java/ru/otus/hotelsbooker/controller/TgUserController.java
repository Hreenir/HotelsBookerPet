package ru.otus.hotelsbooker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.dto.TgUserDto;
import ru.otus.hotelsbooker.service.TgUserService;

@RestController
@RequestMapping("/tguser")
public class TgUserController {
    @Autowired
    private TgUserService tgUsersService;

    @PostMapping(consumes = "application/json", produces = "application/json")
    public TgUserDto createTgUser(@RequestBody TgUserDto tgUserDto){
        return tgUsersService.createTgUser(tgUserDto);
    }

}
