package ru.otus.telegram_bot.commands;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.inject.Named;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.dto.BookingCaseDto;
import ru.otus.telegram_bot.Parser;
import ru.otus.telegram_bot.RoleAuthenticator;
import ru.otus.telegram_bot.client.BookingClient;

import java.util.List;
import java.util.function.BiConsumer;

import static ru.otus.telegram_bot.BotAnswer.INCORRECT_INPUT;
import static ru.otus.telegram_bot.RoleAuthenticator.ROLE_VISITOR;

@Named("/getMyBookings")
@Component
@RequiredArgsConstructor
public class CommandGetBookingsStrategy implements CommandStrategy<List<BookingCaseDto>>{
    private final RoleAuthenticator roleAuthenticator;
    private final ObjectMapper objectMapper;
    private final BookingClient bookingClient;
    @Override
    public List<BookingCaseDto> execute(String messageText, Long chatId, BiConsumer<Long, String> callBack) {
        if (!roleAuthenticator.getRoleByUserId(chatId).equals(ROLE_VISITOR)) {
            callBack.accept(chatId, INCORRECT_INPUT);
            return null;
        }
        try {
            BookingCaseDto bookingCaseDto = BookingCaseDto.builder().tgUserId(chatId).build();
            List<BookingCaseDto> result = bookingClient.get(bookingCaseDto);
            String json = objectMapper.writeValueAsString(result);
            callBack.accept(chatId, json);
        } catch (JsonProcessingException e) {
            callBack.accept(chatId, INCORRECT_INPUT);
        }
        return null;
    }
}
