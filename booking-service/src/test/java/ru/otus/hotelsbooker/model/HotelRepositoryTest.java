package ru.otus.hotelsbooker.model;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.annotation.DirtiesContext;
import ru.otus.dto.RoomDto;
import ru.otus.hotelsbooker.repository.HotelRepository;
import ru.otus.hotelsbooker.repository.RoomRepository;
import ru.otus.hotelsbooker.service.HotelService;
import ru.otus.hotelsbooker.service.RoomService;

import java.math.BigDecimal;
import java.util.List;

@SpringBootTest
@Transactional
@DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
class HotelRepositoryTest {
    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private RoomService roomService;

    @Mock
    private RoomRepository roomRepository;

    @Test
    @DisplayName("Тестирование связности апартоментов с отелем")
    void testThatHotelIdMatchesWithHotelIdAddedRoom() {
        Hotel hotel = hotelRepository.save(Hotel.builder()
                .name("Hilton")
                .city("Moscow")
                .country("Russia")
                .address("address").build());
        Hotel createdHotel = hotelRepository.findAllById(hotel.getId());

        roomService.addRoom(RoomDto.builder()
                .name("Single")
                .capacity(1)
                .priceByDay(new BigDecimal(100))
                .hotelId(hotel.getId())
                .build());

        List <Room> rooms = createdHotel.getRooms();
        Room room = rooms.get(0);

        long expected = room.getHotel().getId();
        long actual = createdHotel.getId();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Тестирование метода поиск всех отелей")
    void testFindByCity() {
        Hotel hotelFirst = hotelRepository.save(Hotel.builder()
                .name("Hilton")
                .city("Москва")
                .country("Россия")
                .address("Красная площадь д.1")
                .build());
        Hotel hotelSecond = hotelRepository.save(Hotel.builder()
                .name("Hilton")
                .city("Нижний Новгород")
                .country("Россия")
                .address("Красная площадь д.1")
                .build());

        List<Hotel> actual = hotelRepository.findAllByCityIgnoreCase("Москва");
        List<Hotel> expected = List.of(
                hotelRepository.findAllById(hotelFirst.getId()));
        Assertions.assertEquals(expected, actual, "invalid");

    }

}
