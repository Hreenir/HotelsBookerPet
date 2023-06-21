package ru.otus.hotelsbooker.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import ru.otus.hotelsbooker.exception.ResourceNotFoundException;
import ru.otus.hotelsbooker.model.Hotel;
import ru.otus.hotelsbooker.model.LocalRoom;
import ru.otus.hotelsbooker.model.Room;
import ru.otus.hotelsbooker.repository.LocalRoomRepository;
import ru.otus.hotelsbooker.repository.RoomRepository;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RoomServiceUnitTest {
    @Mock
    private LocalRoomRepository localRoomRepository;
    @InjectMocks
    private RoomService underTest;
    private final LocalRoom localRoom1 = new LocalRoom(1, 5, true, new Room());
    private final LocalRoom localRoom2 = new LocalRoom(2, 10, true, new Room());
    private final Room room = new Room(1L, "Single", 1, new BigDecimal(100), new Hotel(), List.of(localRoom1, localRoom2));

    @Test
    void findAll_LocalRooms_when_roomId_is_valid() {
        when(localRoomRepository.findAll()).thenReturn(List.of(localRoom1, localRoom2));

        var result = underTest.getAllLocalRooms();
        assertEquals(2, result.size());

        var localRoomDto = result.get(1);
        assertEquals(10, localRoomDto.getRoomNumber());
        assertEquals(2, localRoomDto.getId());
    }

   // @Test
   // void findAll_LocalRooms_when_roomId_is_noValid(){
   //     long roomId = 1;
   //     when(roomRepository.findRoomById(roomId)).thenReturn(null);
   //     roomRepository.findRoomById(roomId);
   //     try {
   //         underTest.getAllLocalRooms();
   //     } catch (ResourceNotFoundException thrown) {
   //         assertEquals("Room with id=1 not found!", thrown.getMessage());
   //     }
   // }
}
