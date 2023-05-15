package ru.otus.telegram_bot.commands;

import jakarta.inject.Named;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.otus.telegram_bot.client.HotelClient;

import java.util.Optional;
import java.util.function.BiConsumer;

@Named("/disablelocalroom")
@Component
@RequiredArgsConstructor
public class CommandDisableLocalRoomStrategy implements CommandStrategy{
    private final HotelClient hotelClient;
    @Override
    public Object execute(String messageText) {
        return null;
    }

    @Override
    public Object execute(long tgUserId, long roleId) {
        return null;
    }

    @Override
    public Object execute(String messageText, long Id) {
        return null;
    }

    @Override
    public Object execute(long LocalRoomId) {
        hotelClient.disableLocalRoom(LocalRoomId);
        return null;
    }

    @Override
    public Object execute(String messageText, long chatId, Optional commandEntity, BiConsumer callBack) {
        return null;
    }
}
