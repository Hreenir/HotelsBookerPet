package ru.otus.hotelsbooker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.hotelsbooker.model.User;

public interface UsersJpaRepository extends JpaRepository<User, Long> {
    public User findByUsername(String login);

}
