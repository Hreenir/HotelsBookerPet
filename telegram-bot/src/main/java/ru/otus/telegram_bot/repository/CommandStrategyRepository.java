package ru.otus.telegram_bot.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;
import ru.otus.telegram_bot.client.AuthenticationClient;
import ru.otus.telegram_bot.client.HotelClient;
import ru.otus.telegram_bot.commands.*;

import java.util.HashMap;
import java.util.Map;
@Repository
public class CommandStrategyRepository {

    private static Map<String, CommandStrategy<?>> strategyMap;
    public CommandStrategyRepository(Map<String, CommandStrategy<?>> strategyMap) {
        CommandStrategyRepository.strategyMap = strategyMap;
    }

    public static Map<String, CommandStrategy<?>> getStrategyMap() {
        return strategyMap;
    }
}
