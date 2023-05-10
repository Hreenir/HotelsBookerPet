package ru.otus.telegram_bot.commands;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.otus.dto.HotelDto;
import ru.otus.telegram_bot.client.HotelClient;


@Qualifier("addhotel")
@Component
@RequiredArgsConstructor
public class CommandAddHotelStrategy implements CommandStrategy<HotelDto>{
    private final HotelClient hotelClient;
    private final ObjectMapper objectMapper;
    @Override
    public HotelDto execute(String messageText) {
        try {
            HotelDto hotelDto = objectMapper.readValue(messageText, HotelDto.class);
            return hotelClient.createHotel(hotelDto);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

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


}
