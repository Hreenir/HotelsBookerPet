package ru.otus.telegram_bot.commands;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import jakarta.inject.Named;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.dto.LocalRoomDto;
import ru.otus.dto.RoomDto;
import ru.otus.telegram_bot.Parser;
import ru.otus.telegram_bot.RoleAuthenticator;
import ru.otus.telegram_bot.client.HotelClient;
import ru.otus.telegram_bot.client.RoomClient;

import java.util.Objects;
import java.util.function.BiConsumer;

import static ru.otus.telegram_bot.BotAnswer.INCORRECT_INPUT;
import static ru.otus.telegram_bot.BotAnswer.INCORRECT_LOCAL_ROOM_ID;
import static ru.otus.telegram_bot.RoleAuthenticator.ROLE_HOTEL;

@Named("/disablelocalroom")
@Component
@RequiredArgsConstructor
public class CommandDisableLocalRoomStrategy implements CommandStrategy<Object> {
    private final  RoomClient roomClient;
    private final RoleAuthenticator roleAuthenticator;
    private final Parser parser;
    private final ObjectMapper objectMapper;

    @Override
    public Object execute(String messageText, Long chatId, BiConsumer<Long, String> callBack) {
        if (roleAuthenticator.getRoleByUserId(chatId) == null) {
            callBack.accept(chatId, INCORRECT_INPUT);
            return null;
        }
        String jsonText = parser.findJsonInString(messageText);
        if (jsonText == null) {
            callBack.accept(chatId, INCORRECT_INPUT);
            return null;
        }
        try {
            LocalRoomDto localRoomDto = objectMapper.readValue(jsonText, LocalRoomDto.class);
            roomClient.disableLocalRoom(localRoomDto);
            callBack.accept(chatId, "Local room with id=" + localRoomDto.getId() + " was disabled.");
        } catch (FeignException e) {
            callBack.accept(chatId, INCORRECT_LOCAL_ROOM_ID);
        } catch (JsonProcessingException e) {
            callBack.accept(chatId, INCORRECT_INPUT);
        }
        return null;
    }
}
