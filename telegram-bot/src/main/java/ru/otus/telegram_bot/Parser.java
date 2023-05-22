package ru.otus.telegram_bot;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
public class Parser {
    private final Pattern VALUE_IN_ROUND_BRACKETS = Pattern.compile("[(](.*?)[)]");
    private final Pattern VALUE_IN_CURLY_BRACKETS = Pattern.compile(".*?\\{([^)]*)\\}.*");
    public String findIdInString(String messageText) {
        try {
            Matcher matcher = VALUE_IN_ROUND_BRACKETS.matcher(messageText);
            matcher.find();
            return matcher.group(1);
        } catch (IllegalStateException e) {
            return null;
        }
    }
    public String findJsonInString(String messageText) {
        try {
            Matcher matcher = VALUE_IN_CURLY_BRACKETS.matcher(messageText);
            matcher.find();
            return '{' + matcher.group(1) + '}';
        } catch (IllegalStateException e) {
            return null;
        }
    }
}
