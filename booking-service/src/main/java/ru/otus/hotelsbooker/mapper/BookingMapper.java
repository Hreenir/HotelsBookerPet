package ru.otus.hotelsbooker.mapper;

import org.springframework.stereotype.Component;
import ru.otus.dto.BookingCaseDto;
import ru.otus.hotelsbooker.model.BookingCase;

@Component
public class BookingMapper {
    public BookingCaseDto bookingToCreateBookingDto(BookingCase bookingCase) {
        return BookingCaseDto.builder()
                .id(bookingCase.getId())
                .localRoomId(bookingCase.getLocalRoom().getId())
                .tgUserId(bookingCase.getTgUser().getId())
                .localRoom(LocalRoomMapper.mapToLocalRoomDto(bookingCase.getLocalRoom()))
                .checkInDate(bookingCase.getCheckInDate())
                .checkOutDate(bookingCase.getCheckOutDate())
                .priceByDay(bookingCase.getPriceByDay())
                .enabled(bookingCase.isEnabled())
                .build();
    }
}
