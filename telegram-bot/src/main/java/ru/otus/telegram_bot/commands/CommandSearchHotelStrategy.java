package ru.otus.telegram_bot.commands;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import jakarta.inject.Named;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.dto.HotelDto;
import ru.otus.dto.SearchDto;
import ru.otus.telegram_bot.Parser;
import ru.otus.telegram_bot.RoleAuthenticator;
import ru.otus.telegram_bot.client.HotelClient;

import java.util.List;
import java.util.function.BiConsumer;

import static ru.otus.telegram_bot.BotAnswer.INCORRECT_INPUT;

@Named("/searchbycity")
@Component
@RequiredArgsConstructor
public class CommandSearchHotelStrategy implements CommandStrategy<List<HotelDto>> {
    private final HotelClient hotelClient;
    private final ObjectMapper objectMapper;
    private final RoleAuthenticator roleAuthenticator;
    private final Parser parser;

    @Override
    public List<HotelDto> execute(String messageText, Long chatId, BiConsumer<Long, String> callBack) {
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
            SearchDto searchDto = objectMapper.readValue(jsonText, SearchDto.class);
            List<HotelDto> result = hotelClient.getAll(searchDto);
            String hotelsJson = objectMapper.writeValueAsString(result);
            callBack.accept(chatId, hotelsJson);
        } catch (JsonProcessingException | FeignException e) {
            callBack.accept(chatId, INCORRECT_INPUT);
        }
        return null;
    }
}
