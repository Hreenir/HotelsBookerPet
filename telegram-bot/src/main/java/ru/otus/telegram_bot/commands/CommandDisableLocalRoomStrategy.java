package ru.otus.telegram_bot.commands;

import feign.FeignException;
import jakarta.inject.Named;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.telegram_bot.Parser;
import ru.otus.telegram_bot.RoleAuthenticator;
import ru.otus.telegram_bot.client.HotelClient;

import java.util.Objects;
import java.util.function.BiConsumer;

import static ru.otus.telegram_bot.BotAnswer.INCORRECT_INPUT;
import static ru.otus.telegram_bot.RoleAuthenticator.ROLE_HOTEL;

@Named("/disablelocalroom")
@Component
@RequiredArgsConstructor
public class CommandDisableLocalRoomStrategy implements CommandStrategy<Object> {
    private final HotelClient hotelClient;
    private final RoleAuthenticator roleAuthenticator;
    private final Parser parser;

    @Override
    public Object execute(String messageText, long chatId, BiConsumer<Long, String> callBack) {
        if (roleAuthenticator.getRoleByUserId(chatId) == null) {
            callBack.accept(chatId, INCORRECT_INPUT);
            return null;
        }
        String localRoomId = parser.findIdInString(messageText);
        if (localRoomId == null) {
            callBack.accept(chatId, INCORRECT_INPUT);
            return null;
        }
        try {
            hotelClient.disableLocalRoom(Long.valueOf(localRoomId));
            callBack.accept(chatId, "Local room with id " + localRoomId + " was disabled.");
        } catch (FeignException e) {
            callBack.accept(chatId, "LocalRoom with id=" + localRoomId + " not found!");
            return null;
        }
        return null;
    }
}
