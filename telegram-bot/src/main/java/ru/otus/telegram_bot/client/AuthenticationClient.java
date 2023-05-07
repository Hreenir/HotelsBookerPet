package ru.otus.telegram_bot.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.otus.dto.TgUserDto;

@FeignClient(url="localhost:8881/tguser", name = "Authentication-client", configuration = FeignBasicAuthInterceptor.class)
public interface AuthenticationClient {
    @PostMapping(consumes = "application/json", produces = "application/json")
    public TgUserDto setRole(@RequestBody TgUserDto tgUserDto);

    @GetMapping("/{id}")
    public TgUserDto findTgUser(@PathVariable Long id);

}
