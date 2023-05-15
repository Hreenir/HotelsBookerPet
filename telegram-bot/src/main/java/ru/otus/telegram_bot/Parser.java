package ru.otus.telegram_bot;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.BiConsumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static ru.otus.telegram_bot.BotAnswer.INCORRECT_INPUT;

@RequiredArgsConstructor
@Service
public class Parser {
    public String findIdInString(String messageText, Long chatId, BiConsumer<Long, String> callBack) {
        try {
            Pattern pattern = Pattern.compile("[(](.*?)[)]");
            Matcher matcher = pattern.matcher(messageText);
            matcher.find();
            return matcher.group(1);
        } catch (IllegalStateException e) {
            callBack.accept(chatId, INCORRECT_INPUT);
            return null;
        }
    }
    public String findJsonInString(String messageText, Long chatId, BiConsumer<Long, String> callBack) {
        try {
            Pattern pattern = Pattern.compile(".*?\\{([^)]*)\\}.*");
            Matcher matcher = pattern.matcher(messageText);
            matcher.find();
            return '{' + matcher.group(1) + '}';
        } catch (IllegalStateException e) {
            callBack.accept(chatId, INCORRECT_INPUT);
            return null;
        }
    }
}
