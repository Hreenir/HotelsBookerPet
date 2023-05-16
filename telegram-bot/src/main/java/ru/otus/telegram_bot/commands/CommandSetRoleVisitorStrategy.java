package ru.otus.telegram_bot.commands;

import jakarta.inject.Named;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import ru.otus.dto.RoleDto;
import ru.otus.dto.TgUserDto;
import ru.otus.telegram_bot.BotAnswer;
import ru.otus.telegram_bot.client.AuthenticationClient;

import java.util.Optional;
import java.util.function.BiConsumer;

import static ru.otus.telegram_bot.RoleAuthenticator.ROLE_VISITOR_ID;

@Named("/setrolevisitor")
@Component
@RequiredArgsConstructor
public class CommandSetRoleVisitorStrategy implements CommandStrategy<TgUserDto> {
    private final AuthenticationClient authenticationClient;

    @Override
    public TgUserDto execute(String messageText, long chatId, BiConsumer<Long, String> callBack) {
        RoleDto roleDto = RoleDto.builder()
                .id(ROLE_VISITOR_ID)
                .build();
        TgUserDto tgUserDto = TgUserDto.builder()
                .id(chatId)
                .role(roleDto)
                .build();
        authenticationClient.setRole(tgUserDto);
        callBack.accept(chatId, BotAnswer.getVISITOR_COMMANDS());
        return null;
    }
}
