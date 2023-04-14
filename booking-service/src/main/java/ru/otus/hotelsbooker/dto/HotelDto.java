package ru.otus.hotelsbooker.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

  public HotelDto(String name, String city, String country, String address) {
    this.name = name;
    this.city = city;
    this.country = country;
    this.address = address;
  }
}
