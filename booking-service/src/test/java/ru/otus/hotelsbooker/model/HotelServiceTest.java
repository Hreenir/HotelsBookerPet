package ru.otus.hotelsbooker.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.dto.HotelDto;
import ru.otus.dto.RoomDto;
import ru.otus.dto.SearchDto;
import ru.otus.hotelsbooker.mapper.HotelMapper;
import ru.otus.hotelsbooker.repository.HotelRepository;
import ru.otus.hotelsbooker.service.HotelService;
import ru.otus.hotelsbooker.service.RoomService;

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
    private RoomService roomService;
    @Autowired
    private HotelRepository hotelRepository;

    @AfterEach
    public void after() {
        hotelService.clearAll();
    }

    @Test
    @DisplayName("Тестирование метода создания отеля с дефолтным рейтингом")
    void ifCreateNewHotelHotelWillBeHaveDefaultRating () {
        Hotel hotel = hotelService.createNewHotel(HotelDto.builder()
                .name("Hilton")
                .city("Москва")
                .country("Россия")
                .address("Красная площадь д.1")
                .build());
        assertEquals(8.0, hotel.getRating());
    }

    @Test
    @DisplayName("Тестирование корректного создания отеля")
    void createdAndSavedHotelCanBeReadAndHotelWillBeHaveTheSameData () {
        Hotel hotel = hotelService.createNewHotel(HotelDto.builder()
                .name("Hilton")
                .city("Москва")
                .country("Россия")
                .address("Красная площадь д.1")
                .build());
        Hotel saved = hotelService.getHotelById(hotel.getId());
        assertEquals(hotel, saved);
    }

  @Test
  @DisplayName("Тестирование метода изменения данных отеля")
  void ifUpdateHotelDataThenHotelDataWillBeUpdated() {
    HotelDto hotel = new HotelDto("Hilton", "Москва", "Россия", "Красная площадь д.1");
    Hotel created = hotelService.createNewHotel(hotel);

    created.setName("Москва");

    Hotel update = hotelService.updateHotel(HotelMapper.mapToDto(created));
    Hotel saved = hotelService.getHotelById(update.getId());

    assertEquals(update, saved);
  }

    @Test
    @DisplayName("Тестирование метода поиск отелей по городу")
    void testThatFindAllWithGivenCityGetsOnlyHotelInTheCity() {
        // prepare
        Hotel hotel1 = hotelService.createNewHotel(HotelDto.builder()
                .name("Hilton")
                .city("Москва")
                .country("Россия")
                .address("Красная площадь д.1")
                .build());
        Hotel hotel2 = hotelService.createNewHotel(HotelDto.builder()
                .name("Hilton")
                .city("Нижний Новгород")
                .country("Россия")
                .address("Красная площадь д.1")
                .build());
        Hotel hotel3 = hotelService.createNewHotel(HotelDto.builder()
                .name("Hilton")
                .city("Москва")
                .country("Россия")
                .address("Красная площадь д.1")
                .build());
        Hotel hotel4 = hotelService.createNewHotel(HotelDto.builder()
                .name("Hilton")
                .city("Санкт-Петербург")
                .country("Россия")
                .address("Красная площадь д.1")
                .build());
        // actions

        List<Hotel> actual = hotelService.findAll(SearchDto.builder().city("Москва").build());
        List<Hotel> expected = List.of(
                hotel1,
                hotel3);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Тестирование метода поиск всех отелей")
    void testThatFindAllWithoutCityGetAllHotels() {
        // prepare
        Hotel hotel1 = hotelService.createNewHotel(HotelDto.builder()
                .name("Hilton")
                .city("Москва")
                .country("Россия")
                .address("Красная площадь д.1")
                .build());
        Hotel hotel2 = hotelService.createNewHotel(HotelDto.builder()
                .name("Hilton")
                .city("Нижний Новгород")
                .country("Россия")
                .address("Красная площадь д.1")
                .build());
        Hotel hotel3 = hotelService.createNewHotel(HotelDto.builder()
                .name("Hilton")
                .city("Москва")
                .country("Россия")
                .address("Красная площадь д.1")
                .build());
        Hotel hotel4 = hotelService.createNewHotel(HotelDto.builder()
                .name("Hilton")
                .city("Санкт-Петербург")
                .country("Россия")
                .address("Красная площадь д.1")
                .build());

        List<Hotel> actual = hotelService.findAll(new SearchDto());
        List<Hotel> expected = List.of(
                hotel1,
                hotel2,
                hotel3,
                hotel4);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Тестирование успешного добавления апартаментов в отель")
    void testSuccessfullyAddRoomToAHotel() {
        Hotel hotel = hotelService.createNewHotel(HotelDto.builder()
                .name("Hilton")
                .city("Moscow")
                .country("Russia")
                .address("address")
                .build());

        Room roomFirst = roomService.addRoom(RoomDto.builder()
                .name("Single")
                .capacity(1)
                .priceByDay(new BigDecimal(100)).
                hotelId(hotel.getId()).
                build());
        Room roomSecond = roomService.addRoom(RoomDto.builder()
                .name("Double")
                .capacity(2)
                .priceByDay(new BigDecimal(100))
                .hotelId(hotel.getId())
                .build());
        List<Room> actual = List.of(roomFirst, roomSecond);
        List<Room> expected = hotelService.getHotelById(hotel.getId()).getRooms();
        Assertions.assertEquals(expected.size(), actual.size());
        for (int i = 0; i < expected.size(); i++) {
            Assertions.assertEquals(expected.get(i).getName(), actual.get(i).getName());
        }
    }
}
