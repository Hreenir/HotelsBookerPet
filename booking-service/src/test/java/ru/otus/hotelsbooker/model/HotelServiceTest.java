package ru.otus.hotelsbooker.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import jakarta.transaction.Transactional;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.hotelsbooker.dto.HotelDto;
import ru.otus.hotelsbooker.service.HotelService;

@SpringBootTest
public class HotelServiceTest {
    //добавить удаление
    // поправить логику
    @Autowired
    private HotelService hotelService;


    @Test
    @DisplayName("Тестирование метода создания отеля с дефолтным рейтингом")
    void ifCreateNewHotelHotelWillBeHaveDefaultRating () {
        HotelDto hotelDto = new HotelDto("Hilton", "Москва", "Россия", "Красная площадь д.1");
        HotelDto savedHotelDto = hotelService.createNewHotel(hotelDto);
        assertEquals(8.0, savedHotelDto.getRating());
    }

    @Test
    @DisplayName("Тестирование метода изменения данных отеля")
    void createdAndSavedHotelCanBeReadAndHotelWillBeHaveTheSameData () {
        //when
        HotelDto hotel = new HotelDto("Hilton", "Москва", "Россия", "Красная площадь д.1");
        HotelDto createdDto = hotelService.createNewHotel(hotel);
        //then
        HotelDto savedDto = hotelService.getHotelById(createdDto.getId());
        assertEquals(createdDto, savedDto);
    }

    @Test
    @DisplayName("Тестирование метода изменения данных отеля")
    void ifUpdateHotelDataThenHotelDataWillBeUpdated() {
        //1. Создать отель, который буду обновлять
        //2. Обновляю его hotelService.updateHotel
        //3. Получаю его снова HotelService.getHotelById
        //4. Сравнить
    }

    @Test
    @DisplayName("Тестирование метода поиск отелей по городу")
    void testThatFindAllWithGivenCityGetsOnlyHotelInTheCity() {
        // prepare
        HotelDto hotel1 = hotelService.createNewHotel(new HotelDto("Hilton", "Москва", "Россия", "Красная площадь д.1"));
        HotelDto hotel2 = hotelService.createNewHotel(new HotelDto("Hilton", "Нижний Новгород", "Россия", "Красная площадь д.1"));
        HotelDto hotel3 = hotelService.createNewHotel(new HotelDto("Hilton", "Москва", "Россия", "Красная площадь д.1"));
        HotelDto hotel4 = hotelService.createNewHotel(new HotelDto("Hilton", "Санкт-Петербург", "Россия", "Красная площадь д.1"));
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
        HotelDto hotel1 = hotelService.createNewHotel(new HotelDto("Hilton", "Москва", "Россия", "Красная площадь д.1"));
        HotelDto hotel2 = hotelService.createNewHotel(new HotelDto("Hilton", "Нижний Новгород", "Россия", "Красная площадь д.1"));
        HotelDto hotel3 = hotelService.createNewHotel(new HotelDto("Hilton", "Москва", "Россия", "Красная площадь д.1"));
        HotelDto hotel4 = hotelService.createNewHotel(new HotelDto("Hilton", "Санкт-Петербург", "Россия", "Красная площадь д.1"));

        List<HotelDto> actual = hotelService.findAll(null);
        List<HotelDto> expected = List.of(
                hotelService.getHotelById(hotel1.getId()),
                hotelService.getHotelById(hotel2.getId()),
                hotelService.getHotelById(hotel3.getId()),
                hotelService.getHotelById(hotel4.getId()));
        assertEquals(expected, actual);
    }
}
