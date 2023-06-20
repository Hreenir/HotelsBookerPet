package ru.otus.hotelsbooker.mapper;

import org.springframework.stereotype.Component;
import ru.otus.dto.CreateBookingDto;
import ru.otus.hotelsbooker.model.BookingCase;

@Component
public class BookingMapper {
    public CreateBookingDto bookingToCreateBookingDto(BookingCase bookingCase) {
        return CreateBookingDto.builder()
                .tgUserId(bookingCase.getTgUser().getId())
                .localRoom(LocalRoomMapper.mapToLocalRoomDto(bookingCase.getLocalRoom()))
                .checkInDate(bookingCase.getCheckInDate())
                .checkOutDate(bookingCase.getCheckOutDate())
                .priceByDay(bookingCase.getPriceByDay())
                .build();
    }
}
