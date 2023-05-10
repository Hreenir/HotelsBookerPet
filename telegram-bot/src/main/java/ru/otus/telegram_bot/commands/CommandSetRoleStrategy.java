package ru.otus.telegram_bot.commands;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.otus.dto.RoleDto;
import ru.otus.dto.TgUserDto;
import ru.otus.telegram_bot.client.AuthenticationClient;

@Qualifier("setrole")
@Component
@RequiredArgsConstructor
public class CommandSetRoleStrategy implements CommandStrategy<TgUserDto> {
    private final AuthenticationClient authenticationClient;
    @Override
    public TgUserDto execute(String messageText) {
        return null;
    }

    @Override
    public TgUserDto execute(long tgUserId, long roleId) {
        RoleDto roleDto = RoleDto.builder()
                .id(roleId)
                .build();
        TgUserDto tgUserDto = TgUserDto.builder()
                .id(tgUserId)
                .role(roleDto)
                .build();
        return authenticationClient.setRole(tgUserDto);
    }

    @Override
    public TgUserDto execute(String messageText, long hotelId) {
        return null;
    }

    @Override
    public TgUserDto execute(long Id) {
        return null;
    }


}