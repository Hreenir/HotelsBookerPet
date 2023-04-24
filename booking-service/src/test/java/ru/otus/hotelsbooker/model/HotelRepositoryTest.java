package ru.otus.hotelsbooker.model;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ru.otus.hotelsbooker.dto.HotelDto;
import ru.otus.hotelsbooker.repository.HotelJpaRepository;
import ru.otus.hotelsbooker.service.HotelService;

import java.util.List;

@SpringBootTest
@Transactional
class HotelRepositoryTest {
    @Autowired
    private HotelJpaRepository hotelJpaRepository;


    @Test
    void testFindByCity() {
        Hotel hotelFirst = hotelJpaRepository.save(Hotel.builder()
                .name("Hilton")
                .city("Москва")
                .country("Россия")
                .address("Красная площадь д.1")
                .build());
        Hotel hotelSecond = hotelJpaRepository.save(Hotel.builder()
                .name("Hilton")
                .city("Нижний Новгород")
                .country("Россия")
                .address("Красная площадь д.1")
                .build());

        List<Hotel> actual = hotelJpaRepository.findAllByCityIgnoreCase("Москва");
        List<Hotel> expected = List.of(
                hotelJpaRepository.findAllById(hotelFirst.getId()));
        Assertions.assertEquals(expected, actual, "invalid");

    }
/*
    @Test
    void testFindAll() {
        HotelDto hotelDtoFirst = hotelService.createNewHotel(new HotelDto("Hilton", "Москва", "Россия", "Красная площадь д.1"));
        HotelDto hotelDtoSecond = hotelService.createNewHotel(new HotelDto("Hilton", "Нижний Новгород", "Россия", "Красная площадь д.1"));
        List<HotelDto> actual = hotelService.findAll(null);
        List<HotelDto> expected = List.of(
                hotelService.getHotelById(hotelDtoFirst.getId()),
                hotelService.getHotelById(hotelDtoSecond.getId()));
        Assertions.assertEquals(expected, actual, "invalid");

    }*/
}
