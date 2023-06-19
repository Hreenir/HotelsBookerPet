package ru.otus.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Calendar;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CreateBookingDto {
    private Long localRoomId;
    private Long tgUserId;
    private LocalRoomDto localRoom;
    private Calendar checkInDate;
    private Calendar checkOutDate;
    private BigDecimal priceByDay;
}
