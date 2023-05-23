package ru.otus.hotelsbooker.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.hotelsbooker.exception.LocalRoomNotFoundException;
import ru.otus.hotelsbooker.model.Hotel;
import ru.otus.hotelsbooker.model.LocalRoom;
import ru.otus.hotelsbooker.model.Room;
import ru.otus.hotelsbooker.repository.HotelJpaRepository;
import ru.otus.hotelsbooker.repository.RoomJpaRepository;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RoomServiceUnitTest {
    @Mock
    private RoomJpaRepository roomJpaRepository;
    @InjectMocks
    private RoomService underTest;
    private final LocalRoom localRoom1 = new LocalRoom(1, 5, true, new Room());
    private final LocalRoom localRoom2 = new LocalRoom(2, 10, true, new Room());
    private final Room room = new Room(1L, "Single", 1, new BigDecimal(100), new Hotel(), List.of(localRoom1, localRoom2));

    @Test
    void findAll_LocalRooms_when_roomId_is_valid() {
        long roomId = 1;
        when(roomJpaRepository.findRoomById(roomId)).thenReturn(room);
        roomJpaRepository.findRoomById(roomId);

        var result = underTest.getAllLocalRooms(roomId);
        assertEquals(2, result.size());

        var localRoomDto = result.get(1);
        assertEquals(10, localRoomDto.getRoomNumber());
        assertEquals(2, localRoomDto.getId());
    }

    @Test
    void findAll_LocalRooms_when_roomId_is_noValid(){
        long roomId = 1;
        when(roomJpaRepository.findRoomById(roomId)).thenReturn(null);
        roomJpaRepository.findRoomById(roomId);
        try {
            underTest.getAllLocalRooms(roomId);
        } catch (LocalRoomNotFoundException thrown) {
            assertNotEquals("", thrown.getMessage());
        }
    }
}
