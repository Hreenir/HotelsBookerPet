package ru.otus.hotelsbooker.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import ru.otus.hotelsbooker.dto.HotelDto;
import ru.otus.hotelsbooker.repository.HotelJpaRepository;
import ru.otus.hotelsbooker.repository.HotelMapRepository;
import ru.otus.hotelsbooker.repository.HotelRepository;
import ru.otus.hotelsbooker.repository.RoomJpaRepository;
import ru.otus.hotelsbooker.service.HotelService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

class HotelRepositoryTest {
    private HotelMapRepository hotelRepository = new HotelMapRepository();


    @Test
    void testFindByCity() {
        List<Hotel> actual = hotelRepository.findAllByCityIgnoreCase("Москва");
        List<Hotel> expected = List.of(
                new Hotel(1L, "Hilton", "Москва", "Россия", 9.6, "Красная площать д.1", new ArrayList<>()),
                new Hotel(3L,"Hilton", "Москва", "Россия", 9.6, "Красная площать д.1", new ArrayList<>()));
        Assertions.assertEquals(expected, actual, "invalid");
    }
    @Test
    void testFindAll() {
        List<Hotel> actual = hotelRepository.findAllByCityIgnoreCase(null);
        List<Hotel> expected = List.of(
                        new Hotel(1L,"Hilton", "Москва", "Россия", 9.6, "Красная площать д.1", new ArrayList<>()),
                        new Hotel(2L,"Hilton", "Нижний Новгород", "Россия", 9.6, "Красная площать д.1", new ArrayList<>()),
                        new Hotel(3L,"Hilton", "Москва", "Россия", 9.6, "Красная площать д.1", new ArrayList<>()),
                        new Hotel(4L,"Hilton", "Санкт-Петербург", "Россия", 9.6, "Красная площать д.1", new ArrayList<>()));
        Assertions.assertEquals(expected, actual, "invalid");
    }
}
