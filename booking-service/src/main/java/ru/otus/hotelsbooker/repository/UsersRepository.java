package ru.otus.hotelsbooker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.hotelsbooker.model.User;

public interface UsersRepository extends JpaRepository<User, Long> {
    User findByUsername(String login);

}
