package ru.otus.hotelsbooker.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.otus.hotelsbooker.repository.HotelRepository;

import java.util.List;

class HotelRepositoryTest {
    private HotelRepository hotelRepository = new HotelRepository();

    @Test
    void testFindByCity() {
        List<Hotel> actual = hotelRepository.findAll("Москва");
        List<Hotel> expected = List.of(
                new Hotel("Hilton", "Москва", "Россия", 9.6, "Красная площать д.1"),
                new Hotel("Hilton", "Москва", "Россия", 9.6, "Красная площать д.1"));
        Assertions.assertEquals(expected, actual, "invalid");
    }
    @Test
    void testFindAll() {
        List<Hotel> actual = hotelRepository.findAll(null);
        List<Hotel> expected = List.of(
                        new Hotel("Hilton", "Москва", "Россия", 9.6, "Красная площать д.1"),
                        new Hotel("Hilton", "Нижний Новгород", "Россия", 9.6, "Красная площать д.1"),
                        new Hotel("Hilton", "Москва", "Россия", 9.6, "Красная площать д.1"),
                        new Hotel("Hilton", "Санкт-Петербург", "Россия", 9.6, "Красная площать д.1"));
        Assertions.assertEquals(expected, actual, "invalid");
    }
}