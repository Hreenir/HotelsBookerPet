package ru.otus.hotelsbooker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.otus.hotelsbooker.model.LocalRoom;

public interface LocalRoomRepository extends JpaRepository<LocalRoom, Long> {
    @Modifying(clearAutomatically = true)
    @Query(value =
                    "update LocalRoom \n" +
                    "set enabled = false\n" +
                    "where id = :localRoomId")
    void disableLocalRoom(@Param("localRoomId") long localRoomId);

    LocalRoom findLocalRoomById(long id);


}
