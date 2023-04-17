package ru.otus.hotelsbooker.dto;

import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Builder
public class RoomDto {
    @Setter(AccessLevel.NONE)
    private Long id;
    private String name;
    private int capacity;
    private BigDecimal priceByDay;
}
