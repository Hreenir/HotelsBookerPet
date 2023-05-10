package ru.otus.telegram_bot.commands;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import feign.codec.DecodeException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.otus.dto.HotelDto;
import ru.otus.dto.RoomDto;
import ru.otus.dto.SearchDto;
import ru.otus.telegram_bot.client.HotelClient;

import java.util.List;

@Qualifier("addroom")
@Component
@RequiredArgsConstructor
public class CommandAddRoomStrategy implements CommandStrategy<RoomDto>{
    private final HotelClient hotelClient;
    private final ObjectMapper objectMapper;
    @Override
    public RoomDto execute(String messageText) {
        return null;
    }

    @Override
    public RoomDto execute(long tgUserId, long roleId) {
        return null;
    }

    @Override
    public RoomDto execute(String messageText, long hotelId) {
        try {
            RoomDto roomDto = objectMapper.readValue(messageText, RoomDto.class);
            return hotelClient.addRoom(roomDto, hotelId);
        } catch (DecodeException | JsonProcessingException e){
            return null;
        }
    }

    @Override
    public RoomDto execute(long Id) {
        return null;
    }
}
