package ru.otus.telegram_bot.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import ru.otus.dto.LocalRoomDto;
import ru.otus.dto.RoomDto;

@FeignClient(url = "localhost:8881/api/v1/rooms", name = "room-client", configuration = FeignBasicAuthInterceptor.class)
public interface RoomClient {
    @PostMapping
    RoomDto addRoom(@RequestBody RoomDto roomDto);
    @PostMapping(path = "/localroom/disable")
    LocalRoomDto disableLocalRoom(@RequestBody LocalRoomDto localRoomDto);
    @PostMapping(path = "/localroom")
    LocalRoomDto addLocalRoom(@RequestBody LocalRoomDto localRoomDto);
}
