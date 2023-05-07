package ru.otus.telegram_bot;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.otus.dto.RoleDto;
import ru.otus.dto.TgUserDto;
import ru.otus.telegram_bot.client.AuthenticationClient;
import ru.otus.telegram_bot.client.HotelClient;
import ru.otus.telegram_bot.commands.BotCommands;
import ru.otus.telegram_bot.commands.CommandSearchHotelStrategy;
import ru.otus.telegram_bot.commands.CommandSetRoleHotelStrategy;
import ru.otus.telegram_bot.commands.CommandStrategy;
import ru.otus.telegram_bot.config.BotConfigurationProperties;

import java.util.Map;
import java.util.Optional;


@Component
@Slf4j
public class BookingTelegramBot extends TelegramLongPollingBot implements BotCommands {
    private final BotConfigurationProperties botConfigurationProperties;
    private final HotelClient hotelClient;
    private final Map<String, CommandStrategy<?>> strategyMap;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final AuthenticationClient authenticationClient;
    private static final long ROLE_HOTEL_ID = 1L;
    private static final long ROLE_VISITOR_ID = 2L;

    @Autowired
    public BookingTelegramBot(
            Map<String, CommandStrategy<?>> strategyMap,
            BotConfigurationProperties botConfigurationProperties,
            HotelClient hotelClient, AuthenticationClient authenticationClient) {
        super(botConfigurationProperties.getToken());
        this.botConfigurationProperties = botConfigurationProperties;
        try {
            this.execute(new SetMyCommands(LIST_OF_COMMANDS, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
        this.hotelClient = hotelClient;
        this.strategyMap = strategyMap;
        this.authenticationClient = authenticationClient;
    }

    @PostConstruct
    public void init() {
        strategyMap.put("search", new CommandSearchHotelStrategy(hotelClient, objectMapper));
        strategyMap.put("setrolehotel", new CommandSetRoleHotelStrategy(authenticationClient));
        strategyMap.put("setrolevisitor", new CommandSetRoleHotelStrategy(authenticationClient));
    }

    @Override
    @SneakyThrows
    public void onUpdateReceived(Update update) {
        String messageText = update.getMessage().getText();
        Long chatId = update.getMessage().getChatId();
        String username = update.getMessage().getFrom().getUserName();

       // if (!hasRole(chatId)) {
       //     callback(chatId, username, "Please, select a role from the menu.");
       // } else {
            Optional<MessageEntity> commandEntity = update.getMessage().getEntities()
                    .stream().filter(e -> "bot_command".equals(e.getType())).findFirst();

            if (commandEntity.isPresent()) {
                String command =
                        update.getMessage()
                                .getText()
                                .substring(commandEntity.get().getOffset(), commandEntity.get().getLength());
                CommandStrategy<?> strategy = strategyMap.get(command.replace("/", ""));
                switch (command) {
                    case "/search":
                        var result = strategy.execute(messageText.substring(commandEntity.get().getLength()));
                        String hotelsJson = objectMapper.writeValueAsString(result);
                        callback(chatId, username, hotelsJson);
                        break;
                    case "/setrolehotel":
                        strategy.execute(chatId, ROLE_HOTEL_ID);
                        break;
                    case "/setrolevisitor":
                        strategy.execute(chatId, ROLE_VISITOR_ID);
                        break;
                }

            }
      //  }
    }

    private void callback(long chatId, String userName, String messageText) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(messageText);

        try {
            execute(message);
            log.info("Reply sent");
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public String getBotUsername() {
        return botConfigurationProperties.getName();
    }

    public boolean hasRole(long id) {
        try {
            TgUserDto tgUserDto = authenticationClient.findTgUser(id);
            return true;
        } catch (RuntimeException e) {
            return false;
        }
    }

    public void setRole(long userId, long roleId) {
        RoleDto roleDto = RoleDto.builder()
                .id(roleId)
                .build();
        TgUserDto tgUserDto = TgUserDto.builder()
                .id(userId)
                .role(roleDto)
                .build();
        authenticationClient.setRole(tgUserDto);
    }

}
