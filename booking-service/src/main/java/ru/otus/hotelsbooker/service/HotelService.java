package ru.otus.hotelsbooker.service;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.hotelsbooker.dto.HotelDto;
import ru.otus.hotelsbooker.model.Hotel;
import ru.otus.hotelsbooker.model.Room;
import ru.otus.hotelsbooker.repository.HotelJpaRepository;

/**
 * Сервис для управления отелями: позволяет создавать, получать данные отеля, искать свободные
 * комнаты
 */
@Service
public class HotelService {

  private final static double DEFAULT_RATING_FOR_NEW_HOTEL = 8.0;
  private final HotelJpaRepository hotelRepository;

  @Autowired
  public HotelService(HotelJpaRepository hotelRepository) {
    this.hotelRepository = hotelRepository;
  }

  public List<Room> findFreeRooms(Hotel hotel, LocalDate arrivalDate, LocalDate departureDate) {
    // поиск свободных номер по датам
    return null;
  }

  public List<Hotel> findAll(String city) {
    return city == null ? hotelRepository.findAll() : hotelRepository.findAllByCityIgnoreCase(city);
  }

  public HotelDto getHotelById(long id) {
    Hotel hotel = hotelRepository.findAllById(id);
    return HotelMapper.mapToDto(hotel);
  }

  // DTO->BL (мы пишем)->DTO
  // createHotel -> createNewHotel
  // createHotel -> newHotel
  // createHotel -> createHotel

  public HotelDto createNewHotel(HotelDto hotelDto) {

    Hotel hotel = Hotel.builder()
        .name(hotelDto.getName())
        .address(hotelDto.getAddress())
        .country(hotelDto.getCountry())
        .city(hotelDto.getCity())
        .rating(DEFAULT_RATING_FOR_NEW_HOTEL)
        .build();

    Hotel createdHotel = hotelRepository.save(hotel);

    return HotelMapper.mapToDto(createdHotel);
  }

  public HotelDto updateHotel(Long id, HotelDto hotelDto) {
    // но сначала написать для тест (TDD)
    // реализовать логики поиска отделя, обновление данных и сохранения
    // только потом подключить контролер
    return null;
  }
}
