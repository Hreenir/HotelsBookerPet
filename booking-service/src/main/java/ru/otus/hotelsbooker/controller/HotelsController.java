package ru.otus.hotelsbooker.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.otus.dto.HotelDto;
import ru.otus.dto.SearchDto;
import ru.otus.hotelsbooker.mapper.HotelMapper;
import ru.otus.hotelsbooker.service.HotelService;

@RestController
@RequestMapping("api/v1/hotels")
@RequiredArgsConstructor
public class HotelsController {
    private final HotelService hotelsService;

    //TODO Добавить построничный вывод
    @PostMapping
    public List<HotelDto> getAll(@RequestBody SearchDto searchDto) {
        return hotelsService.findAll(searchDto).stream()
                .map(HotelMapper::mapToDto)
                .toList();
    }

    @GetMapping("/{id}")
    public HotelDto getById(@PathVariable Long hotelId) {
        return HotelMapper.mapToDto(hotelsService.getHotelById(hotelId));
    }

    @PostMapping("/create")
    public HotelDto create(@RequestBody HotelDto hotelDto) {
        return HotelMapper.mapToDto(hotelsService.createNewHotel(hotelDto));
    }

    @PutMapping
    public HotelDto update(@RequestBody HotelDto hotelDto) {
        return HotelMapper.mapToDto(hotelsService.updateHotel(hotelDto));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long hotelId){
        hotelsService.deleteHotel(hotelId);
    }
}
