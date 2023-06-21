package ru.otus.telegram_bot.commands;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import jakarta.inject.Named;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.dto.BookingCaseDto;
import ru.otus.telegram_bot.Parser;
import ru.otus.telegram_bot.RoleAuthenticator;
import ru.otus.telegram_bot.client.BookingClient;

import java.util.function.BiConsumer;

import static ru.otus.telegram_bot.BotAnswer.INCORRECT_INPUT;
import static ru.otus.telegram_bot.RoleAuthenticator.ROLE_VISITOR;

@Named("/createBooking")
@Component
@RequiredArgsConstructor
public class CommandCreateBookingStrategy implements CommandStrategy<BookingCaseDto>{
    private final RoleAuthenticator roleAuthenticator;
    private final Parser parser;
    private final ObjectMapper objectMapper;
    private final BookingClient bookingClient;
    @Override
    public BookingCaseDto execute(String messageText, Long chatId, BiConsumer<Long, String> callBack) {
        if (!roleAuthenticator.getRoleByUserId(chatId).equals(ROLE_VISITOR)) {
            callBack.accept(chatId, INCORRECT_INPUT);
            return null;
        }
        String jsonText = parser.findJsonInString(messageText);
        if (jsonText == null) {
            callBack.accept(chatId, INCORRECT_INPUT);
            return null;
        }
        try {
            BookingCaseDto bookingCaseDto = objectMapper.readValue(jsonText, BookingCaseDto.class);
            bookingCaseDto.setTgUserId(chatId);
            BookingCaseDto result = bookingClient.create(bookingCaseDto);
            String bookingJson = objectMapper.writeValueAsString(result);
            callBack.accept(chatId, "OK " + bookingJson);
        } catch (JsonProcessingException | FeignException e) {
            callBack.accept(chatId, INCORRECT_INPUT);
        }
        return null;
    }
}
