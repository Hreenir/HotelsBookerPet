package ru.otus.telegram_bot.commands;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import feign.codec.DecodeException;
import jakarta.inject.Named;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.otus.dto.RoomDto;
import ru.otus.dto.TgUserDto;
import ru.otus.telegram_bot.client.AuthenticationClient;
import ru.otus.telegram_bot.client.HotelClient;

import java.util.function.BiConsumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Named("/addroom")
@Component
@RequiredArgsConstructor
public class CommandAddRoomStrategy implements CommandStrategy<RoomDto> {
    private final HotelClient hotelClient;
    private final ObjectMapper objectMapper;
    private final AuthenticationClient authenticationClient;

    @Override
    public RoomDto execute(String messageText) {
        return null;
    }

    @Override
    public RoomDto execute(long tgUserId, long roleId) {
        return null;
    }

    @Override
    public RoomDto execute(String messageText, long Id) {
        return null;
    }

    @Override
    public RoomDto execute(String messageText, Message message, BiConsumer<Long, String> callBack) {
        Long chatId = message.getChatId();
        if (hasRole(chatId) == 1) {
            String hotelId = findIdInString(messageText, chatId);
            if (hotelId != null) {

                try {
                    RoomDto roomDto = objectMapper.readValue(messageText, RoomDto.class);
                    var json = hotelClient.addRoom(roomDto, Long.valueOf(hotelId));
                    String hotelsJson = objectMapper.writeValueAsString(json);
                    if (json == null) {
                        callBack.accept(chatId, "Hotel with id " + hotelId + " not found");
                    } else callBack.accept(chatId, "OK " + hotelsJson);
                } catch (DecodeException | JsonProcessingException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }


    @Override
    public RoomDto execute(long Id) {
        return null;
    }

    private long hasRole(long id) {
        try {
            TgUserDto tgUserDto = authenticationClient.findTgUser(id);
            return tgUserDto.getRole().getId();
        } catch (FeignException e) {
            return 0;
        }
    }

    private String findIdInString(String messageText, long chatId) {
        try {
            Pattern pattern = Pattern.compile("[(](.*?)[)]");
            Matcher matcher = pattern.matcher(messageText);
            matcher.find();
            return matcher.group(1);
        } catch (IllegalStateException e) {
            //callBack(chatId, "incorrect input, try again.");
            return null;
        }
    }
}
