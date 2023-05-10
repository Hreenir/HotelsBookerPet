package ru.otus.telegram_bot.commands;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.otus.telegram_bot.client.HotelClient;
@Qualifier("/disablelocalroom")
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
}
