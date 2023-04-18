package ru.otus.hotelsbooker.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.Collection;
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
    List<HotelDto> actual = hotelService.findAll("Москва");
    List<HotelDto> expected = List.of(
        new HotelDto(1L, "Hilton", "Москва", "Россия", "Красная площадь д.1", 8.0),
        new HotelDto(3L, "Hilton", "Москва", "Россия",  "Красная площадь д.1", 8.0));
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

    List<HotelDto> actual = hotelService.findAll(null);
    List<HotelDto> expected = List.of(
        new HotelDto(1L,"Hilton", "Москва", "Россия", "Красная площать д.1",8.0),
        new HotelDto(2L,"Hilton", "Нижний Новгород", "Россия", "Красная площать д.1",8.0),
        new HotelDto(3L,"Hilton", "Москва", "Россия", "Красная площать д.1",8.0),
        new HotelDto(4L,"Hilton", "Санкт-Петербург", "Россия", "Красная площать д.1",8.0));
    assertEquals(expected, actual);
  }
}
