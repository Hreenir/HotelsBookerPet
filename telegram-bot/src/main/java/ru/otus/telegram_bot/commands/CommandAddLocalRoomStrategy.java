package ru.otus.telegram_bot.commands;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.codec.DecodeException;
import jakarta.inject.Named;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.otus.dto.LocalRoomDto;
import ru.otus.dto.RoomDto;
import ru.otus.dto.SearchDto;
import ru.otus.telegram_bot.client.HotelClient;

import java.util.function.BiConsumer;

@Named("/addlocalroom")
@Component
@RequiredArgsConstructor
public class CommandAddLocalRoomStrategy implements CommandStrategy <LocalRoomDto>{
    private final HotelClient hotelClient;
    private final ObjectMapper objectMapper;

    @Override
    public LocalRoomDto execute(String messageText) {
        return null;
    }

    @Override
    public LocalRoomDto execute(long tgUserId, long roleId) {
        return null;
    }

    @Override
    public LocalRoomDto execute(String messageText, long roomId) {
        try {
            LocalRoomDto localRoomDto = objectMapper.readValue(messageText, LocalRoomDto.class);
            return hotelClient.addLocalRoom(localRoomDto, roomId);
        } catch (DecodeException | JsonProcessingException e){
            return null;
        }

    }

    @Override
    public LocalRoomDto execute(long Id) {
        return null;
    }

    @Override
    public LocalRoomDto execute(String messageText, Message message, BiConsumer<Long, String> callBack) {
        return null;
    }
}
