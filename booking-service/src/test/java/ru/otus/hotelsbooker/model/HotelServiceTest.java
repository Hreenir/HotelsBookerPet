package ru.otus.hotelsbooker.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.hotelsbooker.service.HotelService;

import java.util.List;

@SpringBootTest
public class HotelServiceTest {
    @Autowired
    private HotelService hotelService;
    @Test
    void testFindByCity() {
        List<Hotel> actual = hotelService.findAll("Москва");
        List<Hotel> expected = List.of(
                new Hotel("Hilton", "Москва", "Россия", 9.6, "Красная площать д.1"),
                new Hotel("Hilton", "Москва", "Россия", 9.6, "Красная площать д.1"));
        Assertions.assertEquals(expected, actual, "invalid");
    }
    @Test
    void testFindAll() {
        List<Hotel> actual = hotelService.findAll(null);
        List<Hotel> expected = List.of(
                new Hotel("Hilton", "Москва", "Россия", 9.6, "Красная площать д.1"),
                new Hotel("Hilton", "Нижний Новгород", "Россия", 9.6, "Красная площать д.1"),
                new Hotel("Hilton", "Москва", "Россия", 9.6, "Красная площать д.1"),
                new Hotel("Hilton", "Санкт-Петербург", "Россия", 9.6, "Красная площать д.1"));
        Assertions.assertEquals(expected, actual, "invalid");
    }
}
