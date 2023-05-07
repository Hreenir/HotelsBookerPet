package ru.otus.hotelsbooker.service;

public class TgUserNotFoundException extends RuntimeException {
    public TgUserNotFoundException(String message) {
        super(message);
    }
}