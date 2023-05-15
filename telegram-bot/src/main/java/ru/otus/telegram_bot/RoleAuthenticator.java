package ru.otus.telegram_bot;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.dto.TgUserDto;
import ru.otus.telegram_bot.client.AuthenticationClient;
@RequiredArgsConstructor
@Service
public class RoleAuthenticator {
    public static final long ROLE_HOTEL_ID = 1L;
    public static final long ROLE_VISITOR_ID = 2L;

    private final AuthenticationClient authenticationClient;
    public long hasRole(long id) {
        try {
            TgUserDto tgUserDto = authenticationClient.findTgUser(id);
            return tgUserDto.getRole().getId();
        } catch (FeignException e) {
            return 0;
        }
    }
}
