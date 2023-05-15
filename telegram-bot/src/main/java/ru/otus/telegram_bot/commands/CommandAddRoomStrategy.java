package ru.otus.telegram_bot.commands;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import feign.codec.DecodeException;
import jakarta.inject.Named;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import ru.otus.dto.RoomDto;
import ru.otus.dto.TgUserDto;
import ru.otus.telegram_bot.BotAnswer;
import ru.otus.telegram_bot.RoleAuthenticator;
import ru.otus.telegram_bot.client.AuthenticationClient;
import ru.otus.telegram_bot.client.HotelClient;

import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static ru.otus.telegram_bot.RoleAuthenticator.ROLE_HOTEL_ID;

@Named("/addroom")
@Component
@RequiredArgsConstructor
public class CommandAddRoomStrategy implements CommandStrategy<RoomDto> {
    private final HotelClient hotelClient;
    private final ObjectMapper objectMapper;
    private final RoleAuthenticator roleAuthenticator;

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
    public RoomDto execute(String messageText, long chatId, Optional<MessageEntity> commandEntity, BiConsumer<Long, String> callBack) {

        if (roleAuthenticator.hasRole(chatId) == ROLE_HOTEL_ID) {
            String hotelId = findIdInString(messageText, chatId, callBack);
            if (hotelId != null) {
                try {
                    String jsonText = messageText.substring(commandEntity.get().getLength() + hotelId.length() + 3);
                    RoomDto roomDto = objectMapper.readValue(jsonText, RoomDto.class);
                    var json = hotelClient.addRoom(roomDto, Long.valueOf(hotelId));
                    String roomJson = objectMapper.writeValueAsString(json);
                    callBack.accept(chatId, "OK " + roomJson);
                } catch (DecodeException | JsonProcessingException e) {
                    callBack.accept(chatId, "Hotel with id " + hotelId + " not found.");
                }
            }
        } else callBack.accept(chatId, BotAnswer.getINCORRECT_INPUT());
        return null;
    }


    @Override
    public RoomDto execute(long Id) {
        return null;
    }


    private String findIdInString(String messageText, Long chatId, BiConsumer<Long, String> callBack) {
        try {
            Pattern pattern = Pattern.compile("[(](.*?)[)]");
            Matcher matcher = pattern.matcher(messageText);
            matcher.find();
            return matcher.group(1);
        } catch (IllegalStateException e) {
            callBack.accept(chatId, BotAnswer.getINCORRECT_INPUT());
            return null;
        }
    }
}
