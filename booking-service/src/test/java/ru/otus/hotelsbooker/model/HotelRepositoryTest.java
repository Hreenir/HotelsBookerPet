package ru.otus.hotelsbooker.model;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.annotation.DirtiesContext;
import ru.otus.dto.HotelDto;
import ru.otus.dto.RoomDto;
import ru.otus.hotelsbooker.repository.HotelJpaRepository;
import ru.otus.hotelsbooker.repository.RoomJpaRepository;
import ru.otus.hotelsbooker.service.HotelService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@Transactional
@DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
class HotelRepositoryTest {
    @Autowired
    private HotelJpaRepository hotelJpaRepository;

    @Autowired
    private HotelService hotelService;

    @Mock
    private RoomJpaRepository roomJpaRepository;

    @Test
    @DisplayName("Тестирование связности апартоментов с отелем")
    void testThatHotelIdMatchesWithHotelIdAddedRoom() {
        Hotel hotel = hotelJpaRepository.save(Hotel.builder()
                .name("Hilton")
                .city("Moscow")
                .country("Russia")
                .address("address").build());
        Hotel createdHotel = hotelJpaRepository.findAllById(hotel.getId());

        hotelService.addRoom(new RoomDto("Single", 1, new BigDecimal(100)), hotel.getId());
        List <Room> rooms = createdHotel.getRooms();
        Room room = rooms.get(0);

        long expected = room.getHotel().getId();
        long actual = createdHotel.getId();
        Assertions.assertEquals(expected, actual);
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
