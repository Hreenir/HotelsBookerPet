package ru.otus.telegram_bot.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.otus.dto.RoleDto;
import ru.otus.dto.TgUserDto;
import ru.otus.telegram_bot.client.AuthenticationClient;

@Qualifier("setrolehotel")
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
        RoleDto roleDto = RoleDto.builder()
                .id(roleId)
                .build();
        TgUserDto tgUserDto = TgUserDto.builder()
                .id(tgUserId)
                .role(roleDto)
                .build();
        return authenticationClient.setRole(tgUserDto);
    }
}