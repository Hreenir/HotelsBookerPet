package ru.otus.hotelsbooker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.otus.hotelsbooker.model.BookingCase;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<BookingCase, Long> {

    @Query(nativeQuery = true, value =
            "select * from bookings \n" +
                    "where tg_user_id = :tgUserId")
    List<BookingCase> findAll(@Param("tgUserId") Long tgUserId);
    @Modifying(clearAutomatically = true)
    @Query(value =
            "update BookingCase \n" +
                    "set enabled = false \n" +
                    "where id = :id")
    void cancel(@Param("id") Long bookingId);
    BookingCase findBookingCaseById(Long id);
}
