package ru.otus.telegram_bot.commands;

import jakarta.inject.Named;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.dto.RoleDto;
import ru.otus.dto.TgUserDto;
import ru.otus.telegram_bot.BotAnswer;
import ru.otus.telegram_bot.client.AuthenticationClient;

import java.util.function.BiConsumer;

import static ru.otus.telegram_bot.RoleAuthenticator.ROLE_HOTEL;

@Named("/setrolehotel")
@Component
@RequiredArgsConstructor
public class CommandSetRoleHotelStrategy implements CommandStrategy<TgUserDto> {
    private final AuthenticationClient authenticationClient;

    @Override
    public TgUserDto execute(String messageText, Long chatId, BiConsumer<Long, String> callBack) {
        RoleDto roleDto = RoleDto.builder()
                .name(ROLE_HOTEL)
                .build();
        TgUserDto tgUserDto = TgUserDto.builder()
                .id(chatId)
                .role(roleDto)
                .build();
        authenticationClient.createTgUser(tgUserDto);
        callBack.accept(chatId, BotAnswer.HOTEL_COMMANDS);
        return null;
    }
}