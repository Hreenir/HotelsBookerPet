package ru.otus.hotelsbooker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.hotelsbooker.model.Hotel;

import java.util.List;

public interface HotelJpaRepository extends JpaRepository<Hotel, Long> {
    List<Hotel> findAllByCityIgnoreCase(String name);
    Hotel findAllById(long id);

}
