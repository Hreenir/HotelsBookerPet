package ru.otus.hotelsbooker.exception;

public class LocalRoomNotFoundException extends RuntimeException {
    public LocalRoomNotFoundException(String message) {
        super(message);
    }
}

