package ru.otus.hotelsbooker.model;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ru.otus.hotelsbooker.repository.HotelJpaRepository;

import java.util.List;

@SpringBootTest
@Transactional
@Deprecated
class HotelRepositoryTest {
    @Autowired
    private HotelJpaRepository hotelJpaRepository;
    @AfterEach
    public void clear(){
        List<Hotel> list = hotelJpaRepository.findAll();
        list.forEach(hotel -> hotelJpaRepository.delete(hotel));
    }

    @Test
    @DisplayName("Тестирование метода поиск всех отелей")
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

}
