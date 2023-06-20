package ru.otus.telegram_bot.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.otus.dto.TgUserDto;

@FeignClient(url = "localhost:8881/api/v1/tguser", name = "Authentication-client", configuration = FeignBasicAuthInterceptor.class)
public interface AuthenticationClient {
    @PostMapping
    void createTgUser(@RequestBody TgUserDto tgUserDto);

    @GetMapping(path = "/{id}")
    TgUserDto findTgUser(@PathVariable Long id);

}
