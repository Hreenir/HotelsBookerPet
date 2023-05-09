package ru.otus.telegram_bot;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.codec.DecodeException;
import jakarta.validation.constraints.NotNull;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.otus.telegram_bot.buttons.Buttons;
import ru.otus.telegram_bot.client.AuthenticationClient;
import ru.otus.telegram_bot.commands.*;
import ru.otus.telegram_bot.config.BotConfigurationProperties;

import java.util.Optional;


@Component
@Slf4j
public class BookingTelegramQuickBot extends TelegramLongPollingBot implements QuickBotCommands {
    private final BotConfigurationProperties botConfigurationProperties;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final AuthenticationClient authenticationClient;
    private static final long ROLE_HOTEL_ID = 1L;
    private static final long ROLE_VISITOR_ID = 2L;
    private final CommandStrategyRepository commandStrategyRepository;

    @Autowired
    public BookingTelegramQuickBot(
            BotConfigurationProperties botConfigurationProperties,
            AuthenticationClient authenticationClient,
            CommandStrategyRepository commandStrategyRepository) {
        super(botConfigurationProperties.getToken());
        this.botConfigurationProperties = botConfigurationProperties;
        try {
            this.execute(new SetMyCommands(LIST_OF_COMMANDS, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
        this.authenticationClient = authenticationClient;
        this.commandStrategyRepository = commandStrategyRepository;
    }


    @Override
    @SneakyThrows
    public void onUpdateReceived(@NotNull Update update) {
        Long chatId;
        Message message = update.getMessage();
        String receivedMessage;

        if (update.hasMessage()) {
            receivedMessage = update.getMessage().getText();
            chatId = update.getMessage().getChatId();
            if (!hasRole(chatId)) {
                setRole(chatId);
            } else botAnswerUtils(message, receivedMessage, chatId);

        } else if (update.hasCallbackQuery()) {
            chatId = update.getCallbackQuery().getMessage().getChatId();
            receivedMessage = update.getCallbackQuery().getData();
            botAnswerUtils(message, receivedMessage, chatId);
        }
    }

    private void botAnswerUtils(Message message, String messageText, Long chatId)  {
        String command = "";
        Optional<MessageEntity> commandEntity = Optional.empty();
        CommandStrategy<?> strategy = null;
        if (message == null) {
            strategy = CommandStrategyRepository.getStrategyMap().get(messageText.replace("/", ""));
            command = messageText;
        }
        else {
            commandEntity = message.getEntities()
                    .stream().filter(e -> "bot_command".equals(e.getType())).findFirst();
            if (commandEntity.isPresent()) {
                command =
                        message
                                .getText()
                                .substring(commandEntity.get().getOffset(), commandEntity.get().getLength());
                strategy = CommandStrategyRepository.getStrategyMap().get(command.replace("/", ""));
            }
        }
        switch (command) {
            case "/search":
                var jsonText = strategy.execute(messageText.substring(commandEntity.get().getLength()));
                try {
                    String hotelsJson = objectMapper.writeValueAsString(jsonText);
                    callback(chatId, hotelsJson);
                } catch (JsonProcessingException e){
                    e.printStackTrace();
                }
                break;
            case "/setrolehotel":
                strategy.execute(chatId, ROLE_HOTEL_ID);
                break;
            case "/setrolevisitor":
                strategy.execute(chatId, ROLE_VISITOR_ID);
                break;
        }
    }

    private void callback(long chatId, String messageText) {
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

    private boolean hasRole(long id) {
        try {
            authenticationClient.findTgUser(id);
            return true;
        } catch (DecodeException e){
            return false;
        }
    }

    private void setRole(long chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("Please, select a role.");
        sendMessage.setReplyMarkup(Buttons.showSelectRoleButtons());
        try {
            execute(sendMessage);
            log.info("Reply sent");
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }
}
