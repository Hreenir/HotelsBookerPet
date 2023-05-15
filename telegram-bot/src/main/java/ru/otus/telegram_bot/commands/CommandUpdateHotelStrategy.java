package ru.otus.telegram_bot.commands;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.inject.Named;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import ru.otus.dto.HotelDto;
import ru.otus.dto.SearchDto;
import ru.otus.telegram_bot.BotAnswer;
import ru.otus.telegram_bot.Parser;
import ru.otus.telegram_bot.RoleAuthenticator;
import ru.otus.telegram_bot.client.HotelClient;

import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static ru.otus.telegram_bot.BotAnswer.INCORRECT_INPUT;
import static ru.otus.telegram_bot.RoleAuthenticator.ROLE_HOTEL_ID;

@Named("/updatehotel")
@Component
@RequiredArgsConstructor
public class CommandUpdateHotelStrategy implements CommandStrategy<HotelDto> {
    private final HotelClient hotelClient;
    private final ObjectMapper objectMapper;
    private final RoleAuthenticator roleAuthenticator;
    private final Parser parser;


    @Override
    public HotelDto execute(long tgUserId, long roleId) {
        return null;
    }

    @Override
    public HotelDto execute(String messageText, long hotelId) {
        return null;
    }

    @Override
    public HotelDto execute(long Id) {
        return null;
    }

    @Override
    public HotelDto execute(String messageText, long chatId, Optional<MessageEntity> commandEntity, BiConsumer<Long, String> callBack) {
        if (roleAuthenticator.hasRole(chatId) == ROLE_HOTEL_ID) {
            String hotelId = parser.findIdInString(messageText, chatId, callBack);
            String json = parser.findJsonInString(messageText, chatId, callBack);
            try {
                HotelDto hotelDto = objectMapper.readValue(json, HotelDto.class);
                HotelDto result = hotelClient.updateHotel(Long.valueOf(hotelId), hotelDto);
                String hotelJson = objectMapper.writeValueAsString(result);
                callBack.accept(chatId, "OK " + hotelJson);
            } catch (JsonProcessingException e) {
                callBack.accept(chatId, INCORRECT_INPUT);
            }
        } else callBack.accept(chatId, INCORRECT_INPUT);
        return null;
    }

    @Override
    public HotelDto execute(String messageText) {
        try {
            HotelDto hotelDto = objectMapper.readValue(messageText, HotelDto.class);
            return hotelClient.updateHotel(hotelDto.getId(), hotelDto);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }


}
