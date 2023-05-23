package ru.otus.telegram_bot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.context.annotation.Bean;
import ru.otus.telegram_bot.client.FeignBasicAuthInterceptor;

@SpringBootApplication
@EnableConfigurationProperties
@EnableFeignClients
@ImportAutoConfiguration({FeignAutoConfiguration.class})
public class TelegramBotApplication {
    public static void main(String[] args) {
        SpringApplication.run(TelegramBotApplication.class);
    }

    @Bean
    public FeignBasicAuthInterceptor feignBasicAuthInterceptor() {
        return new FeignBasicAuthInterceptor("user", "user");
    }
}
