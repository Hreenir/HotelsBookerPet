package ru.otus.telegram_bot.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.otus.dto.BookingCaseDto;

import java.util.List;


@FeignClient(url = "localhost:8881/api/v1/bookings", name = "booking-client", configuration = FeignBasicAuthInterceptor.class)
public interface BookingClient {
    @PostMapping
    BookingCaseDto create(@RequestBody BookingCaseDto bookingCaseDto);
    @PostMapping("/get")
    List<BookingCaseDto> get(@RequestBody BookingCaseDto bookingCaseDto);
    @PostMapping("/cancel")
    BookingCaseDto cancel(@RequestBody BookingCaseDto bookingCaseDto);
}
