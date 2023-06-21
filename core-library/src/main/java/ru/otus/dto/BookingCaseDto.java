package ru.otus.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BookingCaseDto {
    private Long id;
    private Long localRoomId;
    private Long tgUserId;
    private LocalRoomDto localRoom;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private BigDecimal priceByDay;
    private boolean enabled;
}
