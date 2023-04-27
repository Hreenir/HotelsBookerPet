package ru.otus.hotelsbooker.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.hotelsbooker.model.Hotel;
import ru.otus.hotelsbooker.repository.HotelJpaRepository;
import ru.otus.hotelsbooker.repository.RoomJpaRepository;

@ExtendWith(MockitoExtension.class)
class HotelServiceUnitTest {

    @Mock
    private HotelJpaRepository hotelRepository;
    @Mock
    private RoomJpaRepository roomJpaRepository;
    @InjectMocks
    private HotelService underTest;

    private final Hotel hotel1 = new Hotel(1L, "Some", "Rostov", "Russia", 1.0, "some address",
        List.of());

    @Test
    void findAll_when_noCity() {
        when(hotelRepository.findAll()).thenReturn(List.of(hotel1));

        var result = underTest.findAll(null);
        assertEquals(1, result.size());

        var hotel = result.get(0);
        assertEquals("Some", hotel.getName());
        assertEquals(1L, hotel.getId());

        verify(hotelRepository).findAll();
    }

    @Test
    void findAll_when_hasCity() {
        when(hotelRepository.findAllByCityIgnoreCase(any())).thenReturn(List.of(hotel1));

        var result = underTest.findAll("Rostov");
        assertEquals(1, result.size());

        verify(hotelRepository).findAllByCityIgnoreCase("Rostov");
    }

}