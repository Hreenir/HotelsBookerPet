package ru.otus.telegram_bot.commands;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.inject.Named;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.dto.HotelDto;
import ru.otus.telegram_bot.Parser;
import ru.otus.telegram_bot.RoleAuthenticator;
import ru.otus.telegram_bot.client.HotelClient;

import java.util.Objects;
import java.util.function.BiConsumer;

import static ru.otus.telegram_bot.BotAnswer.INCORRECT_INPUT;
import static ru.otus.telegram_bot.RoleAuthenticator.ROLE_HOTEL;

@Named("/addhotel")
@Component
@RequiredArgsConstructor
public class CommandAddHotelStrategy implements CommandStrategy<HotelDto> {
    private final HotelClient hotelClient;
    private final ObjectMapper objectMapper;
    private final RoleAuthenticator roleAuthenticator;
    private final Parser parser;

    @Override
    public HotelDto execute(String messageText, Long chatId, BiConsumer<Long, String> callBack) {
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
            HotelDto hotelDto = objectMapper.readValue(jsonText, HotelDto.class);
            HotelDto result = hotelClient.createHotel(hotelDto);
            String hotelJson = objectMapper.writeValueAsString(result);
            callBack.accept(chatId, "OK " + hotelJson);
        } catch (JsonProcessingException e) {
            callBack.accept(chatId, INCORRECT_INPUT);
        }
        return null;
    }
}
