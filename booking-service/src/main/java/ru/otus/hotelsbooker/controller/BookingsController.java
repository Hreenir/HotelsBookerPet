package ru.otus.hotelsbooker.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.otus.dto.BookingCaseDto;
import ru.otus.hotelsbooker.mapper.BookingMapper;
import ru.otus.hotelsbooker.model.BookingCase;
import ru.otus.hotelsbooker.service.BookingService;

import java.util.List;

@RestController
@RequestMapping("api/v1/bookings")
@RequiredArgsConstructor
public class BookingsController {
    private final BookingService bookingService;
    private final BookingMapper bookingMapper;

    @PostMapping
    public BookingCaseDto create(@RequestBody BookingCaseDto bookingCaseDto) {
        BookingCase bookingCase = bookingService.createBooking(bookingCaseDto);
        return bookingMapper.bookingToCreateBookingDto(bookingCase);
    }

    @PostMapping("/get")
    public List<BookingCaseDto> get(@RequestBody BookingCaseDto bookingCaseDto) {
        return bookingService.getBookings(bookingCaseDto).stream()
                .map(bookingMapper::bookingToCreateBookingDto)
                .toList();
    }
    @PostMapping("/cancel")
    public BookingCaseDto cancel(@RequestBody BookingCaseDto bookingCaseDto) {
        return bookingMapper.bookingToCreateBookingDto(bookingService.cancel(bookingCaseDto));
    }
}