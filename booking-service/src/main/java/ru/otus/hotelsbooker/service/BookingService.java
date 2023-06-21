package ru.otus.hotelsbooker.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.dto.BookingCaseDto;
import ru.otus.hotelsbooker.model.BookingCase;
import ru.otus.hotelsbooker.model.LocalRoom;
import ru.otus.hotelsbooker.model.TgUser;
import ru.otus.hotelsbooker.repository.BookingRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final TgUserService tgUserService;
    private final BookingRepository bookingRepository;
    private final RoomService roomService;
    //TODO
    public List<BookingCase> search(String city, LocalDate arrivalDate, LocalDate departureDate) {
        return Collections.emptyList();
    }
    @Transactional
    public BookingCase createBooking(BookingCaseDto bookingCaseDto) {
        TgUser tgUser = tgUserService.getUserById(bookingCaseDto.getTgUserId());
        LocalRoom localRoom = roomService.findLocalRoomById(bookingCaseDto.getLocalRoomId());
        // TODO Проверка того что дата уже занята, это делается sql запросом
        BookingCase bookingCase = BookingCase
                .builder()
                .checkInDate(bookingCaseDto.getCheckInDate())
                .checkOutDate(bookingCaseDto.getCheckOutDate())
                .tgUser(tgUser)
                .priceByDay(BigDecimal.valueOf(100))
                .localRoom(localRoom)
                .enabled(bookingCaseDto.isEnabled())
                .build();

        bookingCase = bookingRepository.save(bookingCase);
        return bookingCase;
    }
    public List<BookingCase> getBookings(BookingCaseDto bookingCaseDto) {
        return bookingRepository.findAll(bookingCaseDto.getTgUserId());
    }
    @Transactional
    public BookingCase cancel(BookingCaseDto bookingCaseDto) {
        bookingRepository.cancel(bookingCaseDto.getId());
        return bookingRepository.findBookingCaseById(bookingCaseDto.getId());
    }
}
