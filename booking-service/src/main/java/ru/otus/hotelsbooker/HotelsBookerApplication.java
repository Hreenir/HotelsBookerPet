package ru.otus.hotelsbooker;

import java.time.LocalDate;
import java.util.List;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.otus.hotelsbooker.model.BookingCase;
import ru.otus.hotelsbooker.service.BookingSearchService;

@SpringBootApplication
public class HotelsBookerApplication {

  public static void main(String[] args) {
//		SpringApplication.run(HotelsBookerApplication.class, args);

    BookingSearchService service = new BookingSearchService();

		List<BookingCase> bookingCases = service.search("Москва", LocalDate.of(2023, 4, 14),
        LocalDate.of(2023, 4, 16));
  }

}
