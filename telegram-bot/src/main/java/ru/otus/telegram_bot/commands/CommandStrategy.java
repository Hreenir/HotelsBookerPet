package ru.otus.telegram_bot.commands;

import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.function.BiConsumer;

public interface CommandStrategy<T> {
    T execute(String messageText);
    T execute(long tgUserId, long roleId);
    T execute(String messageText, long Id);
    T execute(long Id);
    T execute(String messageText, Message message, BiConsumer<Long, String> callBack);
}
