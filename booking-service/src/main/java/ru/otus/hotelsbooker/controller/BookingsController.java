package ru.otus.hotelsbooker.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.dto.CreateBookingDto;
import ru.otus.hotelsbooker.mapper.BookingMapper;
import ru.otus.hotelsbooker.model.BookingCase;
import ru.otus.hotelsbooker.service.BookingService;

@RestController
@RequestMapping("api/v1/bookings")
@RequiredArgsConstructor
public class BookingsController {
    private final BookingService bookingService;
    private final BookingMapper bookingMapper;

    @PostMapping
    public CreateBookingDto createBooking(@RequestBody CreateBookingDto createBookingDto) {
        BookingCase bookingCase = bookingService.createBooking(createBookingDto);
        return bookingMapper.bookingToCreateBookingDto(bookingCase);
    }

    // TODO Доделать управляющие методы
}