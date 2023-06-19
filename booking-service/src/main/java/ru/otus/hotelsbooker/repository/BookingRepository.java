package ru.otus.hotelsbooker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.otus.hotelsbooker.model.BookingCase;

@Repository
public interface BookingRepository extends JpaRepository<BookingCase, Long> {
}
