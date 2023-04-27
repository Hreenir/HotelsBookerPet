package ru.otus.hotelsbooker.service;

import ru.otus.hotelsbooker.model.Room;

public class RoomMapper {
    public static RoomDto mapToRoomDto(Room room) {
        return RoomDto.builder()
                .id(room.getId())
                .name(room.getName())
                .capacity(room.getCapacity())
                .priceByDay(room.getPriceByDay())
                .build();
    }
    public static Room mapToRoom(RoomDto roomDto) {
        return Room.builder()
                .id(roomDto.getId())
                .name(roomDto.getName())
                .capacity(roomDto.getCapacity())
                .priceByDay(roomDto.getPriceByDay())
                .build();
    }
}

