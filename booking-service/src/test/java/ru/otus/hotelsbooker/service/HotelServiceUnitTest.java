package ru.otus.hotelsbooker.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.dto.HotelDto;
import ru.otus.dto.SearchDto;
import ru.otus.hotelsbooker.model.Hotel;
import ru.otus.hotelsbooker.repository.HotelRepository;
import ru.otus.hotelsbooker.repository.RoomRepository;

@ExtendWith(MockitoExtension.class)
class HotelServiceUnitTest {

    @Mock
    private HotelRepository hotelRepository;
    @Mock
    private RoomRepository roomRepository;
    @InjectMocks
    private HotelService underTest;

    private final Hotel hotel1 = new Hotel(1L, "Some", "Rostov", "Russia", 1.0, "some address",
            List.of());

    @Test
    void findAll_when_noCity() {
        when(hotelRepository.findAll()).thenReturn(List.of(hotel1));

        var result = underTest.findAll(new SearchDto());
        assertEquals(1, result.size());

        var hotel = result.get(0);
        assertEquals("Some", hotel.getName());
        assertEquals(1L, hotel.getId());

        verify(hotelRepository).findAll();
    }

    @Test
    void findAll_when_hasCity() {
        when(hotelRepository.findAllByCityIgnoreCase(any())).thenReturn(List.of(hotel1));

        var result = underTest.findAll(SearchDto.builder()
                .city("Rostov").build());
        assertEquals(1, result.size());

        verify(hotelRepository).findAllByCityIgnoreCase("Rostov");
    }

    record UpdateHotelParams(HotelDto toUpdate, Hotel save) {}

    static List<UpdateHotelParams> updateHotel_params() {
        var hotel1 = new Hotel(1L, "Some", "Rostov", "Russia", 1.0, "some address",
            List.of());

        return List.of(
            new UpdateHotelParams(
                new HotelDto("A", "B", "C", "D"),
                new Hotel(1L, "A", "B", "C", hotel1.getRating(), "D", hotel1.getRooms())
                ),
            new UpdateHotelParams(
                new HotelDto("A", null, null, null),
                new Hotel(
                    1L,
                    "A",
                    hotel1.getCity(),
                    hotel1.getCountry(),
                    hotel1.getRating(),
                    hotel1.getAddress(),
                    hotel1.getRooms())
            ),
            new UpdateHotelParams(
                new HotelDto(null, "B", null, null),
                new Hotel(
                    1L,
                    hotel1.getName(),
                    "B",
                    hotel1.getCountry(),
                    hotel1.getRating(),
                    hotel1.getAddress(),
                    hotel1.getRooms())
            )
        );
    }

  // @ParameterizedTest
  // @MethodSource("updateHotel_params")
  // void updateHotel_when_notNull(UpdateHotelParams param) {
  //     when(hotelRepository.findAllById(anyLong())).thenReturn(hotel1);
  //     when(hotelRepository.save(any())).thenReturn(hotel1);

  //     underTest.updateHotel(param.toUpdate);
  //     verify(hotelRepository).save(param.save);
  // }
}
