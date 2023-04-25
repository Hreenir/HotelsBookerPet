package ru.otus.hotelsbooker.model;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import ru.otus.hotelsbooker.repository.HotelJpaRepository;
import ru.otus.hotelsbooker.repository.LocalRoomJpaRepository;
import ru.otus.hotelsbooker.repository.RoomJpaRepository;

import java.math.BigDecimal;
@Transactional
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
        Room room = Room.builder()
                .name("Single")
                .capacity(1)
                .hotel(hotel)
                .priceByDay(new BigDecimal(1100))
                .build();
        localRoom = LocalRoom.builder()
                .roomNumber(1)
                .enabled(true)
                .room(room)
                .build();
        roomJpaRepository.save(room);
        localRoomJpaRepository.save(localRoom);

    }

    @Test
    @DisplayName("Тестирование изменения статуса локальной комнаты")
    public void testDisableLocalRoom() {
        localRoomJpaRepository.disableLocalRoom(localRoom.getId());
        localRoom = localRoomJpaRepository.findById(localRoom.getId()).get();
        Assertions.assertEquals(false, localRoom.isEnabled());
    }
}
