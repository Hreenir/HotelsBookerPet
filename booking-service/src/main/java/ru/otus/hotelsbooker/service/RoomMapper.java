package ru.otus.hotelsbooker.service;

import ru.otus.hotelsbooker.dto.RoomDto;
import ru.otus.hotelsbooker.model.Room;

public class RoomMapper {
    public static RoomDto mapToRoom(Room room) {
        return RoomDto.builder()
                .name(room.getName())
                .capacity(room.getCapacity())
                .priceByDay(room.getPriceByDay())
                .build();
    }
}
