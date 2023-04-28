package ru.otus.hotelsbooker.model;

import jakarta.transaction.Transactional;
import org.aspectj.lang.annotation.After;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import ru.otus.hotelsbooker.repository.HotelJpaRepository;
import ru.otus.hotelsbooker.repository.LocalRoomJpaRepository;
import ru.otus.hotelsbooker.repository.RoomJpaRepository;

import java.math.BigDecimal;
import java.util.List;


@SpringBootTest
public class LocalRoomRepositoryTest {
    @Autowired
    private LocalRoomJpaRepository localRoomJpaRepository;
    @Autowired
    private RoomJpaRepository roomJpaRepository;
    @Autowired
    private HotelJpaRepository hotelJpaRepository;
    private LocalRoom localRoom;

    @BeforeEach
    public void prepare() {
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
        localRoom = localRoomJpaRepository.save(LocalRoom.builder()
                .roomNumber(1)
                .enabled(true)
                .room(room)
                .build());
    }
    @AfterEach
    public void clear(){

        List<LocalRoom> list2 = localRoomJpaRepository.findAll();
        list2.forEach(localRoom -> localRoomJpaRepository.delete(localRoom));

        List<Room> list1 = roomJpaRepository.findAll();
        list1.forEach(room -> roomJpaRepository.delete(room));

        List<Hotel> list = hotelJpaRepository.findAll();
        list.forEach(hotel -> hotelJpaRepository.delete(hotel));
    }

    @Test
    @DisplayName("Тестирование изменения статуса локальной комнаты")
    public void testDisableLocalRoom() {
        localRoomJpaRepository.disableLocalRoom(localRoom.getId());
        localRoom = localRoomJpaRepository.findById(localRoom.getId()).get();
        Assertions.assertEquals(false, localRoom.isEnabled());
    }
}
