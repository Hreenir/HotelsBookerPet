package ru.otus.hotelsbooker.model;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.dto.HotelDto;
import ru.otus.dto.RoomDto;
import ru.otus.hotelsbooker.mapper.RoomMapper;
import ru.otus.hotelsbooker.service.HotelService;
import ru.otus.hotelsbooker.service.RoomService;

import java.math.BigDecimal;
import java.util.List;

@SpringBootTest
@Transactional
@DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
public class RoomServiceTest {
    @Autowired
    private HotelService hotelService;
    @Autowired
    private RoomService roomService;

    @Test
    @DisplayName("Тестирование успешного добавления апартаментов в отель")
    void testSuccessfullyAddRoomToAHotel() {
        Hotel hotel = hotelService.createNewHotel(HotelDto.builder()
                .name("Hilton")
                .city("Moscow")
                .country("Russia")
                .address("address").build());
        RoomDto roomDtoFirst = RoomDto.builder()
                .id(1L)
                .name("Single")
                .capacity(1)
                .priceByDay(new BigDecimal(1100))
                .hotelId(hotel.getId())
                .build();
        RoomDto roomDtoSecond = RoomDto.builder()
                .id(2L)
                .name("Double")
                .capacity(1)
                .priceByDay(new BigDecimal(1100))
                .hotelId(hotel.getId())
                .build();
        roomService.addRoom(roomDtoFirst);
        roomService.addRoom(roomDtoSecond);
        Room first = Room.builder()
                .id(roomDtoFirst.getId())
                .name(roomDtoFirst.getName())
                .capacity(roomDtoFirst.getCapacity())
                .priceByDay(roomDtoFirst.getPriceByDay())
                .hotel(hotelService.getHotelById(hotel.getId()))
                .build();
        Room second = Room.builder()
                .id(roomDtoSecond.getId())
                .name(roomDtoSecond.getName())
                .capacity(roomDtoSecond.getCapacity())
                .priceByDay(roomDtoSecond.getPriceByDay())
                .hotel(hotelService.getHotelById(hotel.getId()))
                .build();
        List<Room> expected = List.of(first, second);
        List<Room> actual = hotelService.getHotelById(hotel.getId()).getRooms();
        Assertions.assertEquals(expected, actual);
    }
    @Test
    @DisplayName("Тестирование связности апартоментов с отелем")
    void testThatHotelIdMatchesWithHotelIdAddedRoom() {
        Hotel hotel = hotelService.createNewHotel(new HotelDto("Hilton", "Moscow", "Russia", "address"));
        Hotel createdHotel = hotelService.getHotelRepository().findAllById(hotel.getId());
        RoomDto roomDtoFirst = RoomDto.builder()
                .id(1L)
                .name("Single")
                .capacity(1)
                .priceByDay(new BigDecimal(100))
                .hotelId(hotel.getId())
                .build();
        roomService.addRoom(roomDtoFirst);
        List <Room> rooms = createdHotel.getRooms();
        Room room = rooms.get(0);

        long expected = room.getHotel().getId();
        long actual = createdHotel.getId();
        Assertions.assertEquals(expected, actual);
    }
}
