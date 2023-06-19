package ru.otus.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Builder
public class LocalRoomDto {
    @Setter(AccessLevel.NONE)
    private Long id;
    private Long roomId;
    private int roomNumber;
    private boolean enabled;
    @EqualsAndHashCode.Exclude
    private RoomDto room;


}
