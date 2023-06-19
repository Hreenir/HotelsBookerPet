package ru.otus.hotelsbooker.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.dto.CreateBookingDto;
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

    public List<BookingCase> search(String city, LocalDate arrivalDate, LocalDate departureDate) {
        return Collections.emptyList();
    }

    public BookingCase createBooking(CreateBookingDto createBookingDto) {
        TgUser tgUser = tgUserService.getUserById(createBookingDto.getTgUserId());
        LocalRoom localRoom = roomService.findLocalRoomById(createBookingDto.getLocalRoomId());
        // TODO Проверка того что дата уже занята, это делается sql запросом
        BookingCase bookingCase = BookingCase
                .builder()
                .checkInDate(createBookingDto.getCheckInDate())
                .checkOutDate(createBookingDto.getCheckOutDate())
                .tgUser(tgUser)
                .priceByDay(BigDecimal.valueOf(100))
                .localRoom(localRoom)
                .build();

        bookingCase = bookingRepository.save(bookingCase);
        return bookingCase;
    }

    // TODO Нужно сделать получение списка брони по тгЮзерИд

    // TODO Нужно сделать отмену брони (удаление)
}
