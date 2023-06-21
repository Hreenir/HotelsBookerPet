package ru.otus.telegram_bot.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.otus.dto.HotelDto;
import ru.otus.dto.LocalRoomDto;
import ru.otus.dto.RoomDto;
import ru.otus.dto.SearchDto;

import java.util.List;

@FeignClient(url = "localhost:8881/api/v1/hotels", name = "hotel-client", configuration = FeignBasicAuthInterceptor.class)
public interface HotelClient {

    @PostMapping
    List<HotelDto> getAll(@RequestBody SearchDto searchDto);
    @GetMapping("/{id}")
    HotelDto getHotelById(@PathVariable Long id);
    @PostMapping("/create")
    HotelDto createHotel(@RequestBody HotelDto hotel);
    @PutMapping
    HotelDto updateHotel(@RequestBody HotelDto hotel);


}
