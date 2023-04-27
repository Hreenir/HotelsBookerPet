package ru.otus.hotelsbooker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.hotelsbooker.model.Hotel;
import ru.otus.hotelsbooker.model.Room;


public interface RoomJpaRepository extends JpaRepository<Room, Long>  {
    Room findAllById(long id);

}
