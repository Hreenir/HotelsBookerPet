package ru.otus.hotelsbooker.service;

import ru.otus.dto.HotelDto;
import ru.otus.dto.RoomDto;
import ru.otus.hotelsbooker.model.Hotel;

import java.util.List;

/**
 * паттерн маппер для отеля
 */
public class HotelMapper {

    public static HotelDto mapToDto(Hotel hotel) {
        List<RoomDto> roomsDto = hotel.getRooms().stream()
                .map(RoomMapper::mapToRoomDto)
                .toList();
        return HotelDto.builder()
                .id(hotel.getId())
                .name(hotel.getName())
                .address(hotel.getAddress())
                .country(hotel.getCountry())
                .city(hotel.getCity())
                .rating(hotel.getRating())
                .rooms(roomsDto)
                .build();
    }
}
