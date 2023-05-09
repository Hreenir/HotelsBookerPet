package ru.otus.telegram_bot.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.stereotype.Repository;
import ru.otus.telegram_bot.client.AuthenticationClient;
import ru.otus.telegram_bot.client.HotelClient;

import java.util.HashMap;
import java.util.Map;
@Repository
public class CommandStrategyRepository {

    private final HotelClient hotelClient;
    private final AuthenticationClient authenticationClient;
    private final ObjectMapper objectMapper;
    private static Map<String, CommandStrategy<?>> strategyMap = new HashMap<>();
    public CommandStrategyRepository(HotelClient hotelClient, AuthenticationClient authenticationClient, ObjectMapper objectMapper, Map<String, CommandStrategy<?>> strategyMap) {
        this.hotelClient = hotelClient;
        this.authenticationClient = authenticationClient;
        this.objectMapper = objectMapper;
        this.strategyMap = strategyMap;
    }
    @PostConstruct
    public void init() {
        strategyMap.put("search", new CommandSearchHotelStrategy(hotelClient, objectMapper));
        strategyMap.put("setrolehotel", new CommandSetRoleHotelStrategy(authenticationClient));
        strategyMap.put("setrolevisitor", new CommandSetRoleHotelStrategy(authenticationClient));
    }

    public static Map<String, CommandStrategy<?>> getStrategyMap() {
        return strategyMap;
    }
}
