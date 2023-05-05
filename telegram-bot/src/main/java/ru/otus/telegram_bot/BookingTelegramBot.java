package ru.otus.telegram_bot;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.otus.dto.HotelDto;
import ru.otus.telegram_bot.client.HotelClient;
import ru.otus.telegram_bot.config.BotConfigurationProperties;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@Slf4j
public class BookingTelegramBot  extends TelegramLongPollingBot {
    private final BotConfigurationProperties botConfigurationProperties;
    private final HotelClient hotelClient;
    private final RestTemplate restTemplate = new RestTemplate();
    private final Map<String, CommandStrategy<?>> strategyMap;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public BookingTelegramBot(
            Map<String, CommandStrategy<?>> strategyMap,
            BotConfigurationProperties botConfigurationProperties,
            HotelClient hotelClient) {
        super(botConfigurationProperties.getToken());
        this.botConfigurationProperties = botConfigurationProperties;
        this.hotelClient = hotelClient;
        this.strategyMap = strategyMap;
    }
    @PostConstruct
    public void init() {
        strategyMap.put("search", new CommandSearchHotelStrategy(hotelClient, objectMapper));
    }

    @Override
    @SneakyThrows
    public void onUpdateReceived(Update update) {
        String messageText = update.getMessage().getText();
        Long chatId = update.getMessage().getChatId();
        String username = update.getMessage().getFrom().getUserName();
        Optional<MessageEntity> commandEntity = update.getMessage().getEntities()
                .stream().filter(e -> "bot_command".equals(e.getType())).findFirst();
        if (commandEntity.isPresent()) {
            String command =
                    update.getMessage()
                            .getText()
                            .substring(commandEntity.get().getOffset(), commandEntity.get().getLength());
            CommandStrategy<?> strategy = strategyMap.get(command.replace("/", ""));
            String string = messageText.substring(commandEntity.get().getLength());
            var result = strategy.execute(string);
            String hotelsJson = objectMapper.writeValueAsString(result);
            callback(chatId, username, hotelsJson);
        }
    }

    private void callback(long chatId, String userName, String messageText) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(messageText);

        try {
            execute(message);
            log.info("Reply sent");
        } catch (TelegramApiException e){
            log.error(e.getMessage());
        }
    }

    @Override
    public String getBotUsername() {
        return botConfigurationProperties.getName();
    }
}
