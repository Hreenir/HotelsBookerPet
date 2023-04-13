package ru.otus.hotelsbooker.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.otus.hotelsbooker.repository.HotelMapRepository;

import java.util.List;

class HotelRepositoryTest {
    private HotelMapRepository hotelRepository = new HotelMapRepository();

    @Test
    void testFindByCity() {
        List<Hotel> actual = hotelRepository.findAllByCityIgnoreCase("Москва");
        List<Hotel> expected = List.of(
                new Hotel(1L,"Hilton", "Москва", "Россия", 9.6, "Красная площать д.1"),
                new Hotel(3L,"Hilton", "Москва", "Россия", 9.6, "Красная площать д.1"));
        Assertions.assertEquals(expected, actual, "invalid");
    }
    @Test
    void testFindAll() {
        List<Hotel> actual = hotelRepository.findAllByCityIgnoreCase(null);
        List<Hotel> expected = List.of(
                        new Hotel(1L,"Hilton", "Москва", "Россия", 9.6, "Красная площать д.1"),
                        new Hotel(2L,"Hilton", "Нижний Новгород", "Россия", 9.6, "Красная площать д.1"),
                        new Hotel(3L,"Hilton", "Москва", "Россия", 9.6, "Красная площать д.1"),
                        new Hotel(4L,"Hilton", "Санкт-Петербург", "Россия", 9.6, "Красная площать д.1"));
        Assertions.assertEquals(expected, actual, "invalid");
    }
}
