package ru.otus.telegram_bot;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.otus.dto.HotelDto;
import ru.otus.telegram_bot.client.HotelClient;

@Qualifier("search")
@Component
@RequiredArgsConstructor
public class CommandSearchHotelStrategy implements CommandStrategy<HotelDto> {
    private final HotelClient hotelClient;
    private final ObjectMapper objectMapper;

    @Override
    public HotelDto execute(String messageText) {
        SearchDto searchDto = objectMapper.readValue(messageText, SearchDto.class);
        return hotelClient.getAllHotels(searchDto.getCity());
    }
}
