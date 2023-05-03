package ru.otus.hotelsbooker.dto;

import lombok.*;
import ru.otus.hotelsbooker.model.Room;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Builder
public class LocalRoomDto {
    @Setter(AccessLevel.NONE)
    private Long id;
    private int roomNumber;
    private boolean enabled;
    @EqualsAndHashCode.Exclude
    private RoomDto room;


}
