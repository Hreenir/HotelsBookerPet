package ru.otus.telegram_bot.commands;

import jakarta.inject.Named;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import ru.otus.dto.RoleDto;
import ru.otus.dto.TgUserDto;
import ru.otus.telegram_bot.BotAnswer;
import ru.otus.telegram_bot.RoleAuthenticator;
import ru.otus.telegram_bot.client.AuthenticationClient;

import java.net.Authenticator;
import java.util.Optional;
import java.util.function.BiConsumer;

import static ru.otus.telegram_bot.RoleAuthenticator.ROLE_HOTEL_ID;

@Named("/setrolehotel")
@Component
@RequiredArgsConstructor
public class CommandSetRoleHotelStrategy implements CommandStrategy<TgUserDto> {
    private final AuthenticationClient authenticationClient;

    @Override
    public TgUserDto execute(String messageText) {
        return null;
    }

    @Override
    public TgUserDto execute(long tgUserId, long roleId) {
        return null;
    }

    @Override
    public TgUserDto execute(String messageText, long hotelId) {
        return null;
    }

    @Override
    public TgUserDto execute(long Id) {
        return null;
    }

    @Override
    public TgUserDto execute(String messageText, long chatId, Optional<MessageEntity> commandEntity, BiConsumer<Long, String> callBack) {
        RoleDto roleDto = RoleDto.builder()
                .id(ROLE_HOTEL_ID)
                .build();
        TgUserDto tgUserDto = TgUserDto.builder()
                .id(chatId)
                .role(roleDto)
                .build();
        authenticationClient.setRole(tgUserDto);
        callBack.accept(chatId, BotAnswer.getHOTEL_COMMANDS());
        return null;
    }
}