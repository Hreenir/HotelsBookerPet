package ru.otus.telegram_bot;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
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
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.otus.dto.TgUserDto;
import ru.otus.telegram_bot.buttons.Buttons;
import ru.otus.telegram_bot.client.AuthenticationClient;
import ru.otus.telegram_bot.commands.*;
import ru.otus.telegram_bot.repository.CommandStrategyRepository;
import ru.otus.telegram_bot.config.BotConfigurationProperties;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static ru.otus.telegram_bot.RoleAuthenticator.NO_ROLE;


@Component
@Slf4j
public class BookingTelegramQuickBot extends TelegramLongPollingBot implements QuickBotCommands {
    private final BotConfigurationProperties botConfigurationProperties;
    private final AuthenticationClient authenticationClient;

    @Autowired
    public BookingTelegramQuickBot(BotConfigurationProperties botConfigurationProperties, AuthenticationClient authenticationClient) {
        super(botConfigurationProperties.getToken());
        this.botConfigurationProperties = botConfigurationProperties;
        try {
            this.execute(new SetMyCommands(LIST_OF_COMMANDS, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
        this.authenticationClient = authenticationClient;
    }

    @Override
    @SneakyThrows
    public void onUpdateReceived(@NotNull Update update) {
        Long chatId;
        Message message = update.getMessage();
        String receivedMessage;

        if (update.hasMessage()) {
            chatId = update.getMessage().getChatId();
            receivedMessage = update.getMessage().getText();
            if (hasRole(chatId) == NO_ROLE) {
                setRole(chatId);
            } else botAnswerUtilsForText(message, receivedMessage, chatId);

        } else if (update.hasCallbackQuery()) {
            chatId = update.getCallbackQuery().getMessage().getChatId();
            receivedMessage = update.getCallbackQuery().getData();
            botAnswerUtilsForButton(receivedMessage, chatId);
        }
    }
    private void botAnswerUtilsForText(Message message, String messageText, Long chatId) {
        Optional<MessageEntity> commandEntity = message.getEntities()
                .stream().filter(e -> "bot_command".equals(e.getType())).findFirst();
        if (commandEntity.isPresent()) {
            String command =
                    message
                            .getText()
                            .substring(commandEntity.get().getOffset(), commandEntity.get().getLength());
            CommandStrategy<?> strategy = CommandStrategyRepository.getStrategyMap().get(command);
            strategy.execute(messageText, chatId, this::callBack);
        }
    }

    private void botAnswerUtilsForButton(String messageText, Long chatId) {
        CommandStrategy<?> strategy = CommandStrategyRepository.getStrategyMap().get(messageText);
            strategy.execute(messageText, chatId, this::callBack);
    }

    private void callBack(long chatId, String messageText) {
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

    private void callBackWihButtons(long chatId, String messageText, InlineKeyboardMarkup markup) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(messageText);
        message.setReplyMarkup(markup);

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

    private long hasRole(long id) {
        try {
            TgUserDto tgUserDto = authenticationClient.findTgUser(id);
            return tgUserDto.getRole().getId();
        } catch (FeignException e) {
            return 0;
        }
    }

    private void setRole(long chatId) {
        callBackWihButtons(chatId, "Please, select a role.", Buttons.showSelectRoleButtons());
    }
}
