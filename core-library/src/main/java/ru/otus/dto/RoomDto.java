package ru.otus.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Builder
@ToString
public class RoomDto {
    @Setter(AccessLevel.NONE)
    private Long id;
    private Long hotelId;
    private String name;
    private int capacity;
    private BigDecimal priceByDay;
}
