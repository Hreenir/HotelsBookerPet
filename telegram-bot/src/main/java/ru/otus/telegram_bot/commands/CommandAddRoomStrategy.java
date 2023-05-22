package ru.otus.telegram_bot.commands;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.codec.DecodeException;
import jakarta.inject.Named;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.dto.RoomDto;
import ru.otus.telegram_bot.Parser;
import ru.otus.telegram_bot.RoleAuthenticator;
import ru.otus.telegram_bot.client.HotelClient;

import java.util.Objects;
import java.util.function.BiConsumer;

import static ru.otus.telegram_bot.BotAnswer.INCORRECT_INPUT;
import static ru.otus.telegram_bot.RoleAuthenticator.ROLE_HOTEL;

@Named("/addroom")
@Component
@RequiredArgsConstructor
public class CommandAddRoomStrategy implements CommandStrategy<RoomDto> {
    private final HotelClient hotelClient;
    private final ObjectMapper objectMapper;
    private final RoleAuthenticator roleAuthenticator;
    private final Parser parser;

    @Override
    public RoomDto execute(String messageText, long chatId, BiConsumer<Long, String> callBack) {

        if (!Objects.equals(roleAuthenticator.hasRole(chatId), ROLE_HOTEL)) {
            callBack.accept(chatId, INCORRECT_INPUT);
            return null;
        }
        String hotelId = parser.findIdInString(messageText);
        if (hotelId == null) {
            callBack.accept(chatId, INCORRECT_INPUT);
            return null;
        }
        String jsonText = parser.findJsonInString(messageText);
        if (jsonText == null) {
            callBack.accept(chatId, INCORRECT_INPUT);
            return null;
        }
        try {
            RoomDto roomDto = objectMapper.readValue(jsonText, RoomDto.class);
            var json = hotelClient.addRoom(roomDto, Long.valueOf(hotelId));
            String roomJson = objectMapper.writeValueAsString(json);
            callBack.accept(chatId, "OK " + roomJson);
        } catch (JsonProcessingException | DecodeException e) {
            callBack.accept(chatId, "Hotel with id " + hotelId + " not found.");
        }
        return null;
    }
}
