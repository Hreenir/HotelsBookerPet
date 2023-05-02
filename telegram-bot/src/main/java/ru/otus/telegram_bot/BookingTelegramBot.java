package ru.otus.telegram_bot;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.otus.telegram_bot.config.BotConfigurationProperties;

import java.util.Optional;

@Component
@Slf4j
public class BookingTelegramBot  extends TelegramLongPollingBot {
    private final BotConfigurationProperties botConfigurationProperties;
    private final RestTemplate restTemplate = new RestTemplate();

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public BookingTelegramBot(BotConfigurationProperties botConfigurationProperties) {
        super(botConfigurationProperties.getToken());
        this.botConfigurationProperties = botConfigurationProperties;
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
            switch (command) {
                case "start":
                    callback(chatId, username, "COMMAND LIST");
                    break;
            }
        }
            String city = "Moscow";
        // /search city=Moscow
        // switch-case: "search": hotelBokkerCLient.search(city=Moscow)
        //ResponseEntity<List> forEntity = restTemplate.getForEntity("http://localhost:8081/hotel?city=" + city, List.class);
        // GET localhost:8881/hotel?city=Moscow
        // List<Hotel> hotels
        //String valueAsString = objectMapper.writeValueAsString("COMMAND LIST");
        //pingPongBot(chatId, username, "COMMAND LIST");
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
