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
            if (hasRole(chatId) == 0) {
                setRole(chatId);
            } else botAnswerUtils(message, receivedMessage, chatId);

        } else if (update.hasCallbackQuery()) {
            chatId = update.getCallbackQuery().getMessage().getChatId();
            receivedMessage = update.getCallbackQuery().getData();
            botAnswerUtils(message, receivedMessage, chatId);
        }
    }

    private void botAnswerUtils(Message message, String messageText, Long chatId) {
        String command = "";
        Optional<MessageEntity> commandEntity = Optional.empty();
        CommandStrategy<?> strategy = null;
        if (message == null) {
            strategy = CommandStrategyRepository.getStrategyMap().get(messageText.replace("/", ""));
            command = messageText;
        } else {
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
        Object json;
        switch (command) {
            case "/searchbycity":
                if (hasRole(chatId) == 2) {
                    json = strategy.execute(messageText.substring(commandEntity.get().getLength()));
                    try {
                        String hotelsJson = objectMapper.writeValueAsString(json);
                        callBack(chatId, hotelsJson);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case "/setrolehotel":
                strategy.execute(chatId, ROLE_HOTEL_ID);
                callBack(chatId, BotAnswer.getHOTEL_COMMANDS());
                break;
            case "/setrolevisitor":
                strategy.execute(chatId, ROLE_VISITOR_ID);
                callBack(chatId, BotAnswer.getVISITOR_COMMANDS());
                break;
            case "/addhotel", "/updatehotel":
                if (hasRole(chatId) == 1) {
                    json = strategy.execute(messageText.substring(commandEntity.get().getLength()));
                    try {
                        String hotelsJson = objectMapper.writeValueAsString(json);
                        callBack(chatId, "OK " + hotelsJson);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case "/addroom":
                if (hasRole(chatId) == 1) {
                    String hotelId = findIdInString(messageText, chatId);
                    if (hotelId != null) {
                        try {
                            json = strategy.execute(messageText.substring(commandEntity.get().getLength() + hotelId.length() + 3), Long.parseLong(hotelId));
                            String hotelsJson = objectMapper.writeValueAsString(json);
                            if (json == null) {
                                callBack(chatId, "Hotel with id " + hotelId + " not found");
                            } else callBack(chatId, "OK " + hotelsJson);
                        } catch (JsonProcessingException e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
            case "/addlocalroom":
                if (hasRole(chatId) == 1) {
                    String roomId = findIdInString(messageText, chatId);
                    if (roomId != null) {
                        try {
                            json = strategy.execute(messageText.substring(commandEntity.get().getLength() + roomId.length() + 3), Long.parseLong(roomId));
                            String hotelsJson = objectMapper.writeValueAsString(json);
                            if (json == null) {
                                callBack(chatId, "Room with id " + roomId + " not found");
                            } else callBack(chatId, "OK " + hotelsJson);
                        } catch (JsonProcessingException e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
            case "/disablelocalroom":
                if (hasRole(chatId) == 1) {
                    String localRoomId = findIdInString(messageText, chatId);
                    if (localRoomId != null) {
                        strategy.execute(Integer.parseInt(localRoomId));
                    }
                }

        }
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

    private String findIdInString(String messageText, long chatId) {
        try {
            Pattern pattern = Pattern.compile("[(](.*?)[)]");
            Matcher matcher = pattern.matcher(messageText);
            matcher.find();
            return matcher.group(1);
        } catch (IllegalStateException e) {
            callBack(chatId, "incorrect input, try again.");
            return null;
        }
    }
}
