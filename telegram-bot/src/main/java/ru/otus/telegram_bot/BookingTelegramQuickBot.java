package ru.otus.telegram_bot;

import jakarta.validation.constraints.NotNull;
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
import ru.otus.telegram_bot.buttons.Buttons;
import ru.otus.telegram_bot.buttons.QuickBotCommands;
import ru.otus.telegram_bot.client.AuthenticationClient;
import ru.otus.telegram_bot.commands.*;
import ru.otus.telegram_bot.repository.CommandStrategyRepository;
import ru.otus.telegram_bot.config.BotConfigurationProperties;

import java.util.Optional;


@Component
@Slf4j
public class BookingTelegramQuickBot extends TelegramLongPollingBot implements QuickBotCommands {
    private final BotConfigurationProperties botConfigurationProperties;
    private final RoleAuthenticator roleAuthenticator;

    @Autowired
    public BookingTelegramQuickBot(BotConfigurationProperties botConfigurationProperties, AuthenticationClient authenticationClient, RoleAuthenticator roleAuthenticator) {
        super(botConfigurationProperties.getToken());
        this.botConfigurationProperties = botConfigurationProperties;
        this.roleAuthenticator = roleAuthenticator;
        try {
            this.execute(new SetMyCommands(LIST_OF_COMMANDS, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }

    public void onUpdateReceived(@NotNull Update update) {
        Long chatId;
        Message message = update.getMessage();
        String receivedMessage;

        if (update.hasMessage()) {
            chatId = update.getMessage().getChatId();
            receivedMessage = update.getMessage().getText();
            if (roleAuthenticator.getRoleByUserId(chatId) == null) {
                setRole(chatId);
            } else botAnswerUtilsForText(message, receivedMessage, chatId);

        } else if (update.hasCallbackQuery()) {
            chatId = update.getCallbackQuery().getMessage().getChatId();
            receivedMessage = update.getCallbackQuery().getData();
            botAnswerUtilsForButton(receivedMessage, chatId);
        }
    }

    private void botAnswerUtilsForText(Message message, String messageText, Long chatId) {
        try {
            Optional<MessageEntity> commandEntity = message.getEntities()
                    .stream().filter(e -> "bot_command".equals(e.getType())).findFirst();
            if (commandEntity.isPresent()) {
                String command =
                        message
                                .getText()
                                .substring(commandEntity.get().getOffset(), commandEntity.get().getLength());
                CommandStrategy<?> strategy = CommandStrategyRepository.getStrategy(command);
                strategy.execute(messageText, chatId, this::callBack);
            }
        } catch (NullPointerException e) {
            callBack(chatId, BotAnswer.INCORRECT_INPUT);
        }
    }

    private void botAnswerUtilsForButton(String messageText, Long chatId) {
        CommandStrategy<?> strategy = CommandStrategyRepository.getStrategy(messageText);
        strategy.execute(messageText, chatId, this::callBack);
    }

    private void callBack(long chatId, String messageText) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(messageText);

        try {
            execute(message);
            log.info("Reply sent UserId = " + chatId);
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }

    private void callBackWihButtons(long chatId, String messageText, InlineKeyboardMarkup markup) {
        SendMessage message = SendMessage.builder()
                .chatId(chatId)
                .text(messageText)
                .replyMarkup(markup).build();
        try {
            execute(message);
            log.info("Reply sent UserId = " + chatId);
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }

    public String getBotUsername() {
        return botConfigurationProperties.getName();
    }

    private void setRole(long chatId) {
        callBackWihButtons(chatId, "Please, select a role.", Buttons.showSelectRoleButtons());
    }
}
