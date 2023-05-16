package ru.otus.hotelsbooker.exception;

public class TgUserNotFoundException extends RuntimeException {
    public TgUserNotFoundException(String message) {
        super(message);
    }
}