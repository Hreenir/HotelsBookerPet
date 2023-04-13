package ru.otus.hotelsbooker.model;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.hotelsbooker.dto.HotelDto;
import ru.otus.hotelsbooker.service.HotelService;

import java.util.List;

@SpringBootTest
@Transactional
public class HotelServiceTest {

    @Autowired
    private HotelService hotelService;

    @Test
    void testFindByCity() {
        // prepare
        HotelDto hotel1 = new HotelDto("Hilton", "Москва", "Россия", "Красная площать д.1");
        HotelDto hotel2 = new HotelDto("Hilton", "Нижний Новгород", "Россия", "Красная площать д.1");
        HotelDto hotel3 = new HotelDto("Hilton", "Москва", "Россия", "Красная площать д.1");
        HotelDto hotel4 = new HotelDto("Hilton", "Санкт-Петербург", "Россия", "Красная площать д.1");
        hotelService.save(hotel1);
        hotelService.save(hotel2);
        hotelService.save(hotel3);
        hotelService.save(hotel4);
        // actions
        List<Hotel> actual = hotelService.findAll("Москва");
        List<Hotel> expected = List.of(
                new Hotel(1L,"Hilton", "Москва", "Россия", 8.0, "Красная площать д.1"),
                new Hotel(3L,"Hilton", "Москва", "Россия", 8.0, "Красная площать д.1"));
        Assertions.assertEquals(expected, actual, "invalid");
    }

    @Test
    void testFindAll() {
        // prepare
        HotelDto hotel1 = new HotelDto("Hilton", "Москва", "Россия", "Красная площать д.1");
        HotelDto hotel2 = new HotelDto("Hilton", "Нижний Новгород", "Россия", "Красная площать д.1");
        HotelDto hotel3 = new HotelDto("Hilton", "Москва", "Россия", "Красная площать д.1");
        HotelDto hotel4 = new HotelDto("Hilton", "Санкт-Петербург", "Россия", "Красная площать д.1");
        hotelService.save(hotel1);
        hotelService.save(hotel2);
        hotelService.save(hotel3);
        hotelService.save(hotel4);

        List<Hotel> actual = hotelService.findAll(null);
        List<Hotel> expected = List.of(
                new Hotel(1L,"Hilton", "Москва", "Россия", 8.0, "Красная площать д.1"),
                new Hotel(2L,"Hilton", "Нижний Новгород", "Россия", 8.0, "Красная площать д.1"),
                new Hotel(3L,"Hilton", "Москва", "Россия", 8.0, "Красная площать д.1"),
                new Hotel(4L,"Hilton", "Санкт-Петербург", "Россия", 8.0, "Красная площать д.1"));
        Assertions.assertEquals(expected, actual, "invalid");
    }
}
