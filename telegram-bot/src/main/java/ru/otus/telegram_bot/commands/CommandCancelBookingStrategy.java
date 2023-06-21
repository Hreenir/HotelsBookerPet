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

import static ru.otus.telegram_bot.BotAnswer.*;
import static ru.otus.telegram_bot.RoleAuthenticator.ROLE_VISITOR;

@Named("/cancelBooking")
@Component
@RequiredArgsConstructor
public class CommandCancelBookingStrategy implements CommandStrategy<BookingCaseDto>{
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
        bookingClient.cancel(bookingCaseDto);
            callBack.accept(chatId, "Booking with id=" + bookingCaseDto.getId() + " was cancelled.");
        } catch (FeignException e) {
            callBack.accept(chatId, INCORRECT_BOOKING_ID);
        } catch (JsonProcessingException e) {
            callBack.accept(chatId, INCORRECT_INPUT);
        }

        return null;
    }
}
