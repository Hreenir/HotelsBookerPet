package ru.otus.hotelsbooker.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.otus.hotelsbooker.model.Room;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class HotelDto {

  @Setter(AccessLevel.NONE)
  private Long id;
  private String name;
  private String city;
  private String country;
  private String address;
  @Setter(AccessLevel.NONE)
  private Double rating;
  @EqualsAndHashCode.Exclude
  private List<RoomDto> rooms = new ArrayList<>();

  public HotelDto(String name, String city, String country, String address) {
    this.name = name;
    this.city = city;
    this.country = country;
    this.address = address;
  }

  public HotelDto(Long id, String name, String city, String country, String address, Double rating) {
    this.id = id;
    this.name = name;
    this.city = city;
    this.country = country;
    this.address = address;
    this.rating = rating;
  }
}
