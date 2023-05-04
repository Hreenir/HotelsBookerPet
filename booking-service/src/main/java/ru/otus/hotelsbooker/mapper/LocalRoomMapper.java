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
}
