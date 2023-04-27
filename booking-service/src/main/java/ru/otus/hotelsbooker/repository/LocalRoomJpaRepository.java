package ru.otus.hotelsbooker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.otus.hotelsbooker.model.Hotel;
import ru.otus.hotelsbooker.model.LocalRoom;
import ru.otus.hotelsbooker.model.Role;

import java.util.Set;

public interface LocalRoomJpaRepository extends JpaRepository<LocalRoom, Long> {
    @Modifying(clearAutomatically = true)
    @Query(nativeQuery = true, value =
            "update local_rooms \n" +
                    "set enabled = false\n" +
                    "where id = :localRoomId")
    public void disableLocalRoom(@Param("localRoomId") long localRoomId);



}
