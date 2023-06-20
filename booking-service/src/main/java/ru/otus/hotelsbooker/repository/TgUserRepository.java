package ru.otus.hotelsbooker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.otus.hotelsbooker.model.TgUser;

public interface TgUserRepository extends JpaRepository<TgUser, Long> {
    TgUser findTgUserById(long tgUserId);
}
