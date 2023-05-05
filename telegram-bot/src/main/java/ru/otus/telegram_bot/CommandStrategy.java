package ru.otus.telegram_bot;

public interface CommandStrategy<T> {
    T execute(String messageText);
}
