package ru.otus.telegram_bot;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.dto.TgUserDto;
import ru.otus.telegram_bot.client.AuthenticationClient;
@RequiredArgsConstructor
@Service
public class RoleAuthenticator {
    public static final String ROLE_HOTEL = "ROLE_HOTEL";
    public static final String ROLE_VISITOR = "ROLE_VISITOR";

    private final AuthenticationClient authenticationClient;
    public String getRoleByUserId(long id) {
        try {
            TgUserDto tgUserDto = authenticationClient.findTgUser(id);
            return tgUserDto.getRole().getName();
        } catch (FeignException e) {
            return null;
        }
    }
}
