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
import ru.otus.telegram_bot.client.HotelClient;

import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;

@Named("/updatehotel")
@Component
@RequiredArgsConstructor
public class CommandUpdateHotelStrategy implements CommandStrategy<HotelDto>{
    private final HotelClient hotelClient;
    private final ObjectMapper objectMapper;



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
        return null;
    }
    @Override
    public HotelDto execute(String messageText) {
        try {
            HotelDto hotelDto = objectMapper.readValue(messageText, HotelDto.class);
            return hotelClient.updateHotel(hotelDto.getId(), hotelDto);
        } catch (JsonProcessingException e){
            e.printStackTrace();
        }
        return null;
    }
}
