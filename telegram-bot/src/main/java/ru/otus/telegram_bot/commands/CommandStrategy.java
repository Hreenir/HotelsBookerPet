package ru.otus.telegram_bot.commands;

public interface CommandStrategy<T> {
    T execute(String messageText);
    T execute(long tgUserId, long roleId);

}
