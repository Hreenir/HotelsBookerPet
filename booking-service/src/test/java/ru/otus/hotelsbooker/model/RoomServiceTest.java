package ru.otus.hotelsbooker.model;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.dto.HotelDto;
import ru.otus.dto.RoomDto;
import ru.otus.hotelsbooker.service.HotelService;
import ru.otus.hotelsbooker.service.RoomService;

import java.math.BigDecimal;
import java.util.List;

@SpringBootTest
@Transactional
public class RoomServiceTest {
    @Autowired
    private HotelService hotelService;
    @Autowired
    private RoomService roomService;


    @Test
    @DisplayName("Тестирование успешного добавления апартаментов в отель")
    void testSuccessfullyAddRoomToAHotel() {
        HotelDto hotelDto = hotelService.createNewHotel(new HotelDto("Hilton", "Moscow", "Russia", "address"));
        RoomDto roomDtoFirst = roomService.addRoom(new RoomDto(1L,"Single", 1, new BigDecimal(100)), hotelDto.getId());
        RoomDto roomDtoSecond = roomService.addRoom(new RoomDto(2L,"Double", 2, new BigDecimal(100)), hotelDto.getId());
        List<RoomDto> actual = List.of(roomDtoFirst, roomDtoSecond);
        List<RoomDto> expected = hotelService.getHotelById(hotelDto.getId()).getRooms();
        Assertions.assertEquals(expected, actual);
    }
    @Test
    @DisplayName("Тестирование связности апартоментов с отелем")
    void testThatHotelIdMatchesWithHotelIdAddedRoom() {
        HotelDto hotelDto = hotelService.createNewHotel(new HotelDto("Hilton", "Moscow", "Russia", "address"));
        Hotel createdHotel = hotelService.getHotelRepository().findAllById(hotelDto.getId());
        roomService.addRoom(new RoomDto("Single", 1, new BigDecimal(100)), hotelDto.getId());
        List <Room> rooms = createdHotel.getRooms();
        Room room = rooms.get(0);

        long expected = room.getHotel().getId();
        long actual = createdHotel.getId();
        Assertions.assertEquals(expected, actual);
    }
}
