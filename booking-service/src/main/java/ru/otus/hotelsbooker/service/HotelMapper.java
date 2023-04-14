package ru.otus.hotelsbooker.service;

import ru.otus.hotelsbooker.dto.HotelDto;
import ru.otus.hotelsbooker.model.Hotel;

/**
 * паттерн маппер для отеля
 */
public class HotelMapper {

  public static HotelDto mapToDto(Hotel hotel) {
    return HotelDto.builder()
        .id(hotel.getId())
        .name(hotel.getName())
        .address(hotel.getAddress())
        .country(hotel.getCountry())
        .city(hotel.getCity())
        .rating(hotel.getRating())
        .build();
  }

}
