package ru.otus.hotelsbooker.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.dto.HotelDto;
import ru.otus.dto.RoomDto;
import ru.otus.hotelsbooker.repository.HotelJpaRepository;
import ru.otus.hotelsbooker.service.HotelService;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
@DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
public class HotelServiceTest {
    //почему не удаляется?
    @Autowired
    private HotelService hotelService;
    @Autowired
    private HotelJpaRepository hotelJpaRepository;

    @AfterEach
    public void after() {
        hotelService.clearAll();
    }

    @Test
    @DisplayName("Тестирование метода создания отеля с дефолтным рейтингом")
    void ifCreateNewHotelHotelWillBeHaveDefaultRating () {
        HotelDto hotelDto = hotelService.createNewHotel(HotelDto.builder()
                .name("Hilton")
                .city("Москва")
                .country("Россия")
                .address("Красная площадь д.1")
                .build());
        assertEquals(8.0, hotelDto.getRating());
    }

    @Test
    @DisplayName("Тестирование корректного создания отеля")
    void createdAndSavedHotelCanBeReadAndHotelWillBeHaveTheSameData () {
        HotelDto hotelDto = hotelService.createNewHotel(HotelDto.builder()
                .name("Hilton")
                .city("Москва")
                .country("Россия")
                .address("Красная площадь д.1")
                .build());
        HotelDto savedDto = hotelService.getHotelById(hotelDto.getId());
        assertEquals(hotelDto, savedDto);
    }

  @Test
  @DisplayName("Тестирование метода изменения данных отеля")
  void ifUpdateHotelDataThenHotelDataWillBeUpdated() {
    HotelDto hotel = new HotelDto("Hilton", "Москва", "Россия", "Красная площадь д.1");
    HotelDto createdDto = hotelService.createNewHotel(hotel);

    createdDto.setName("Москва");

    HotelDto updateDto = hotelService.updateHotel(createdDto.getId(), createdDto);
    HotelDto savedDto = hotelService.getHotelById(updateDto.getId());

    assertEquals(updateDto, savedDto);
  }






    @Test
    @DisplayName("Тестирование метода поиск отелей по городу")
    void testThatFindAllWithGivenCityGetsOnlyHotelInTheCity() {
        // prepare
        HotelDto hotel1 = hotelService.createNewHotel(HotelDto.builder()
                .name("Hilton")
                .city("Москва")
                .country("Россия")
                .address("Красная площадь д.1")
                .build());
        HotelDto hotel2 = hotelService.createNewHotel(HotelDto.builder()
                .name("Hilton")
                .city("Нижний Новгород")
                .country("Россия")
                .address("Красная площадь д.1")
                .build());
        HotelDto hotel3 = hotelService.createNewHotel(HotelDto.builder()
                .name("Hilton")
                .city("Москва")
                .country("Россия")
                .address("Красная площадь д.1")
                .build());
        HotelDto hotel4 = hotelService.createNewHotel(HotelDto.builder()
                .name("Hilton")
                .city("Санкт-Петербург")
                .country("Россия")
                .address("Красная площадь д.1")
                .build());
        // actions
        List<HotelDto> actual = hotelService.findAll("Москва");
        List<HotelDto> expected = List.of(
                hotel1,
                hotel3);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Тестирование метода поиск всех отелей")
    void testThatFindAllWithoutCityGetAllHotels() {
        // prepare
        HotelDto hotel1 = hotelService.createNewHotel(HotelDto.builder()
                .name("Hilton")
                .city("Москва")
                .country("Россия")
                .address("Красная площадь д.1")
                .build());
        HotelDto hotel2 = hotelService.createNewHotel(HotelDto.builder()
                .name("Hilton")
                .city("Нижний Новгород")
                .country("Россия")
                .address("Красная площадь д.1")
                .build());
        HotelDto hotel3 = hotelService.createNewHotel(HotelDto.builder()
                .name("Hilton")
                .city("Москва")
                .country("Россия")
                .address("Красная площадь д.1")
                .build());
        HotelDto hotel4 = hotelService.createNewHotel(HotelDto.builder()
                .name("Hilton")
                .city("Санкт-Петербург")
                .country("Россия")
                .address("Красная площадь д.1")
                .build());

        List<HotelDto> actual = hotelService.findAll(null);
        List<HotelDto> expected = List.of(
                hotel1,
                hotel2,
                hotel3,
                hotel4);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Тестирование успешного добавления апартаментов в отель")
    void testSuccessfullyAddRoomToAHotel() {
        HotelDto hotelDto = hotelService.createNewHotel(new HotelDto("Hilton", "Moscow", "Russia", "address"));
        RoomDto roomDtoFirst = hotelService.addRoom(new RoomDto("Single", 1, new BigDecimal(100)), hotelDto.getId());
        RoomDto roomDtoSecond = hotelService.addRoom(new RoomDto("Double", 2, new BigDecimal(100)), hotelDto.getId());
        List<RoomDto> actual = List.of(roomDtoFirst, roomDtoSecond);
        List<RoomDto> expected = hotelService.getHotelById(hotelDto.getId()).getRooms();
        Assertions.assertEquals(expected.size(), actual.size());
        for (int i = 0; i < expected.size(); i++) {
            Assertions.assertEquals(expected.get(i).getName(), actual.get(i).getName());
        }
    }
}
