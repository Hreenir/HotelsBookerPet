package ru.otus.telegram_bot.commands;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.otus.dto.HotelDto;
import ru.otus.dto.SearchDto;
import ru.otus.telegram_bot.client.HotelClient;

import java.util.List;

@Qualifier("search")
@Component
@RequiredArgsConstructor
public class CommandSearchHotelStrategy implements CommandStrategy<List<HotelDto>> {
    private final HotelClient hotelClient;
    private final ObjectMapper objectMapper;

    @Override
    public List<HotelDto> execute(String messageText) {
        try {
            SearchDto searchDto = objectMapper.readValue(messageText, SearchDto.class);
            return hotelClient.getAllHotels(searchDto.getCity());
        } catch (JsonProcessingException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<HotelDto> execute(long tgUserId, long roleId) {
        return null;
    }
}
