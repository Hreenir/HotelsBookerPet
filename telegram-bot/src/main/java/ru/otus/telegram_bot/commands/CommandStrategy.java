package ru.otus.telegram_bot.commands;

import org.telegram.telegrambots.meta.api.objects.MessageEntity;

import java.util.Optional;
import java.util.function.BiConsumer;

public interface CommandStrategy<T> {
    T execute(String messageText, Long chatId, BiConsumer<Long, String> callBack);
}
