package ru.otus.hotelsbooker.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import jakarta.transaction.Transactional;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.hotelsbooker.dto.HotelDto;
import ru.otus.hotelsbooker.service.HotelService;

@SpringBootTest
@Transactional
public class HotelServiceTest {

  @Autowired
  private HotelService hotelService;

  @Test
  void если_создать_новый_отель_то_по_умолчанию_у_него_будет_рейтинг_8() {
    HotelDto hotelDto = new HotelDto("Hilton", "Москва", "Россия", "Красная площадь д.1");
    HotelDto savedHotelDto = hotelService.createNewHotel(hotelDto);
    assertEquals(8.0, savedHotelDto.getRating());
  }

  @Test
  void созданный_и_сохраненный_отель_можно_прочитать_и_у_него_будут_такие_же_данные() {
    //when
    HotelDto hotel = new HotelDto("Hilton", "Москва", "Россия", "Красная площадь д.1");
    HotelDto createdDto = hotelService.createNewHotel(hotel);
    //then
    HotelDto savedDto = hotelService.getHotelById(createdDto.getId());
    assertEquals(createdDto, savedDto);
  }

  @Test
  void если_обновить_данные_отеля_то_после_его_получения_у_него_будут_эти_данные(){
    //1. Создать отель, который буду обновлять
    //2. Обновляю его hotelService.updateHotel
    //3. Получаю его снова HotelService.getHotelById
    //4. Сравнить
  }

  @Test
  void test_that_findAll_with_given_city_gets_only_hotel_in_the_city() {
    // prepare
    HotelDto hotel1 = new HotelDto("Hilton", "Москва", "Россия", "Красная площадь д.1");
    HotelDto hotel2 = new HotelDto("Hilton", "Нижний Новгород", "Россия", "Красная площадь д.1");
    HotelDto hotel3 = new HotelDto("Hilton", "Москва", "Россия", "Красная площадь д.1");
    HotelDto hotel4 = new HotelDto("Hilton", "Санкт-Петербург", "Россия", "Красная площадь д.1");
    hotelService.createNewHotel(hotel1);
    hotelService.createNewHotel(hotel2);
    hotelService.createNewHotel(hotel3);
    hotelService.createNewHotel(hotel4);
    // actions
    double rating = 8.0;
    List<Hotel> actual = hotelService.findAll("Москва");
    List<Hotel> expected = List.of(
        new Hotel(1L, "Hilton", "Москва", "Россия", rating, "Красная площать д.1"),
        new Hotel(3L, "Hilton", "Москва", "Россия", rating, "Красная площать д.1"));
    assertEquals(expected, actual);
  }

  @Test
  void test_that_findAll_without_city_get_all_hotels() {
    // prepare
    HotelDto hotel1 = new HotelDto("Hilton", "Москва", "Россия", "Красная площать д.1");
    HotelDto hotel2 = new HotelDto("Hilton", "Нижний Новгород", "Россия", "Красная площать д.1");
    HotelDto hotel3 = new HotelDto("Hilton", "Москва", "Россия", "Красная площать д.1");
    HotelDto hotel4 = new HotelDto("Hilton", "Санкт-Петербург", "Россия", "Красная площать д.1");
    hotelService.createNewHotel(hotel1);
    hotelService.createNewHotel(hotel2);
    hotelService.createNewHotel(hotel3);
    hotelService.createNewHotel(hotel4);

    List<Hotel> actual = hotelService.findAll(null);
    List<Hotel> expected = List.of(
        new Hotel(1L, "Hilton", "Москва", "Россия", 8.0, "Красная площать д.1"),
        new Hotel(2L, "Hilton", "Нижний Новгород", "Россия", 8.0, "Красная площать д.1"),
        new Hotel(3L, "Hilton", "Москва", "Россия", 8.0, "Красная площать д.1"),
        new Hotel(4L, "Hilton", "Санкт-Петербург", "Россия", 8.0, "Красная площать д.1"));
    assertEquals(expected, actual);
  }
}
