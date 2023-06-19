package ru.otus.hotelsbooker.model;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.hotelsbooker.repository.HotelRepository;
import ru.otus.hotelsbooker.repository.LocalRoomRepository;
import ru.otus.hotelsbooker.repository.RoomRepository;

import java.math.BigDecimal;
import java.util.List;

@Transactional
@SpringBootTest
@DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
public class LocalRoomRepositoryTest {
    @Autowired
    private LocalRoomRepository localRoomRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private HotelRepository hotelRepository;

    @AfterEach
    public void clear(){

        List<LocalRoom> list2 = localRoomRepository.findAll();
        list2.forEach(localRoom -> localRoomRepository.delete(localRoom));

        List<Room> list1 = roomRepository.findAll();
        list1.forEach(room -> roomRepository.delete(room));

        List<Hotel> list = hotelRepository.findAll();
        list.forEach(hotel -> hotelRepository.delete(hotel));
    }

    @Test
    @DisplayName("Тестирование изменения статуса локальной комнаты")
    public void testDisableLocalRoom() {
        Hotel hotel = hotelRepository.save(Hotel.builder()
                .name("Hilton")
                .city("Москва")
                .country("Россия")
                .address("Красная площадь д.1")
                .build());
        Room room = roomRepository.save(Room.builder()
                .name("Single")
                .capacity(1)
                .hotel(hotel)
                .priceByDay(new BigDecimal(1100))
                .build());
        LocalRoom localRoom = localRoomRepository.save(LocalRoom.builder()
                .roomNumber(1)
                .enabled(true)
                .room(room)
                .build());
        localRoomRepository.disableLocalRoom(localRoom.getId());
        localRoom = localRoomRepository.findById(localRoom.getId()).get();
        Assertions.assertEquals(false, localRoom.isEnabled());
    }
   /* @Test
    @DisplayName("Тестирование добавления локальной комнаты")
    public void testAddLocalRoom(){
        Hotel hotel = hotelJpaRepository.save(Hotel.builder()
                .name("Hilton")
                .city("Москва")
                .country("Россия")
                .address("Красная площадь д.1")
                .build());
        Room room = roomJpaRepository.save(Room.builder()
                .name("Single")
                .capacity(1)
                .hotel(hotel)
                .priceByDay(new BigDecimal(1100))
                .build());
        LocalRoom localRoom = LocalRoom.builder()
                .roomNumber(1)
                .enabled(true)
                .room(room)
                .build();
        LocalRoom savedLocalRoom = localRoomJpaRepository.save(localRoom);
        Assertions.assertEquals(localRoom,savedLocalRoom);
    }*/
}
