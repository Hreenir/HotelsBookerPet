package ru.otus.telegram_bot;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.dto.HotelDto;
import ru.otus.telegram_bot.client.HotelClient;

import java.util.List;

@SpringBootTest
@DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
public class TestBotApplication {
    @Autowired
    HotelClient hotelClient;
    @Disabled
    @Test
    public void testContextUp() {
        List<HotelDto> allHotels = hotelClient.getAllHotels("Moscow");
        Assertions.assertNotNull(allHotels);
    }
}
