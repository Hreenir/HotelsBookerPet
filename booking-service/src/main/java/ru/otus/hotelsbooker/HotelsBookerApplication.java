package ru.otus.hotelsbooker;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HotelsBookerApplication {

  public static void main(String[] args) {
		SpringApplication.run(HotelsBookerApplication.class, args);

   //BookingSearchService service = new BookingSearchService();

//	List<BookingCase> bookingCases = service.search("Москва", LocalDate.of(2023, 4, 14),
   //    LocalDate.of(2023, 4, 16));
  }

}
