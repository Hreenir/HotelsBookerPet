package ru.otus.hotelsbooker.model;

import java.util.List;

public class HotelRepository {

  private final List<Hotel> allHotels = List.of(
    new Hotel("Hilton", "Москва", "Россия", 9.6, "Красная площать д.1"),
  )

  public List<Hotel> findAll(String city) {

    return null;
  }

}
