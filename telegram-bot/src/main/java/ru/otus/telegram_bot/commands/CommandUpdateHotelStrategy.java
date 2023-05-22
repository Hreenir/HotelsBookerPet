package ru.otus.telegram_bot.commands;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.codec.DecodeException;
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

@Named("/updatehotel")
@Component
@RequiredArgsConstructor
public class CommandUpdateHotelStrategy implements CommandStrategy<HotelDto> {
    private final HotelClient hotelClient;
    private final ObjectMapper objectMapper;
    private final RoleAuthenticator roleAuthenticator;
    private final Parser parser;

    @Override
    public HotelDto execute(String messageText, long chatId, BiConsumer<Long, String> callBack) {
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
            HotelDto hotelDto = objectMapper.readValue(jsonText, HotelDto.class);
            HotelDto result = hotelClient.updateHotel(Long.valueOf(hotelId), hotelDto);
            String hotelJson = objectMapper.writeValueAsString(result);
            callBack.accept(chatId, "OK " + hotelJson);
        } catch (JsonProcessingException | DecodeException e) {
            callBack.accept(chatId, INCORRECT_INPUT);
        }
        return null;
    }
}
