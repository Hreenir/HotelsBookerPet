package ru.otus.hotelsbooker.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.otus.dto.TgUserDto;
import ru.otus.hotelsbooker.mapper.TgUserMapper;
import ru.otus.hotelsbooker.service.TgUserService;

@RestController
@RequestMapping("api/v1/tguser")
@RequiredArgsConstructor
public class TgUserController {
    private final TgUserService tgUsersService;

    @PostMapping
    public TgUserDto createTgUser(@RequestBody TgUserDto tgUserDto) {
        return TgUserMapper.mapToTgUserDto(tgUsersService.createTgUser(tgUserDto));
    }

    @GetMapping("/{id}")
    public TgUserDto findTgUser(@PathVariable Long id) {
        return TgUserMapper.mapToTgUserDto(tgUsersService.getUserById(id));
    }
}
