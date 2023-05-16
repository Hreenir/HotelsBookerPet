package ru.otus.telegram_bot.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.codec.DecodeException;
import jakarta.inject.Named;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import ru.otus.dto.LocalRoomDto;
import ru.otus.telegram_bot.Parser;
import ru.otus.telegram_bot.RoleAuthenticator;
import ru.otus.telegram_bot.client.HotelClient;

import java.util.Optional;
import java.util.function.BiConsumer;

import static ru.otus.telegram_bot.BotAnswer.INCORRECT_INPUT;
import static ru.otus.telegram_bot.RoleAuthenticator.ROLE_HOTEL_ID;

@Named("/disablelocalroom")
@Component
@RequiredArgsConstructor
public class CommandDisableLocalRoomStrategy implements CommandStrategy<Object> {
    private final HotelClient hotelClient;
    private final RoleAuthenticator roleAuthenticator;
    private final Parser parser;

    @Override
    public Object execute(String messageText, long chatId, BiConsumer<Long, String> callBack) {
        if (roleAuthenticator.hasRole(chatId) != ROLE_HOTEL_ID) {
            callBack.accept(chatId, INCORRECT_INPUT);
            return null;
        }
        String localRoomId = parser.findIdInString(messageText);
        if (localRoomId == null) {
            callBack.accept(chatId, INCORRECT_INPUT);
            return null;
        }
        try {
            LocalRoomDto result = hotelClient.disableLocalRoom(Long.valueOf(localRoomId));
            callBack.accept(chatId, "Local room with id " + localRoomId + " was disabled.");
        } catch (DecodeException e) {
            callBack.accept(chatId, "LocalRoom with id=" + localRoomId + " not found!");
            return null;
        }
        return null;
    }
}
