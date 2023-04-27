package ru.otus.dto;

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

    public RoomDto(String name, int capacity, BigDecimal priceByDay) {
        this.name = name;
        this.capacity = capacity;
        this.priceByDay = priceByDay;
    }
}
