package ru.otus.hotelsbooker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.hotelsbooker.model.Room;


public interface RoomRepository extends JpaRepository<Room, Long>  {
    @Override
    Room save(Room entity);
    Room findRoomById(long id);

}
