package ru.otus.hotelsbooker.mapper;

import ru.otus.dto.LocalRoomDto;
import ru.otus.hotelsbooker.model.LocalRoom;

public class LocalRoomMapper {
    public static LocalRoom mapToLocalRoom(LocalRoomDto localRoomDto) {
        return  LocalRoom.builder()
                .roomNumber(localRoomDto.getRoomNumber())
                .enabled(localRoomDto.isEnabled())
                .build();
    }
    public static LocalRoomDto mapToLocalRoomDto(LocalRoom localRoom) {
        return  LocalRoomDto.builder()
                .id(localRoom.getId())
                .roomNumber(localRoom.getRoomNumber())
                .enabled(localRoom.isEnabled())
                .room(RoomMapper.mapToRoomDto(localRoom.getRoom()))
                .build();
    }
}
