package ru.otus.telegram_bot.commands;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import feign.codec.DecodeException;
import jakarta.inject.Named;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.dto.LocalRoomDto;
import ru.otus.telegram_bot.Parser;
import ru.otus.telegram_bot.RoleAuthenticator;
import ru.otus.telegram_bot.client.HotelClient;
import ru.otus.telegram_bot.client.RoomClient;

import java.util.Objects;
import java.util.function.BiConsumer;

import static ru.otus.telegram_bot.BotAnswer.INCORRECT_INPUT;
import static ru.otus.telegram_bot.BotAnswer.INCORRECT_ROOM_ID;
import static ru.otus.telegram_bot.RoleAuthenticator.ROLE_HOTEL;

@Named("/addLocalRoom")
@Component
@RequiredArgsConstructor
public class CommandAddLocalRoomStrategy implements CommandStrategy<LocalRoomDto> {
    private final RoomClient roomClient;
    private final ObjectMapper objectMapper;
    private final RoleAuthenticator roleAuthenticator;
    private final Parser parser;

    @Override
    public LocalRoomDto execute(String messageText, Long chatId, BiConsumer<Long, String> callBack) {
        if (!roleAuthenticator.getRoleByUserId(chatId).equals(ROLE_HOTEL)) {
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
            LocalRoomDto result = roomClient.addLocalRoom(localRoomDto);
            String LocalRoomJson = objectMapper.writeValueAsString(result);
            callBack.accept(chatId, "OK " + LocalRoomJson);
        } catch (JsonProcessingException e) {
            callBack.accept(chatId, INCORRECT_INPUT);
        } catch (FeignException e) {
            callBack.accept(chatId, INCORRECT_ROOM_ID);
        }
        return null;
    }
}
