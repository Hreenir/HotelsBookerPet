package ru.otus.telegram_bot;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


@RequiredArgsConstructor
@Service
public class Parser {
    public String findIdInString(String messageText) {
        try {
            Pattern pattern = Pattern.compile("[(](.*?)[)]");
            Matcher matcher = pattern.matcher(messageText);
            matcher.find();
            return matcher.group(1);
        } catch (IllegalStateException e) {
            return null;
        }
    }
    public String findJsonInString(String messageText) {
        try {
            Pattern pattern = Pattern.compile(".*?\\{([^)]*)\\}.*");
            Matcher matcher = pattern.matcher(messageText);
            matcher.find();
            return '{' + matcher.group(1) + '}';
        } catch (IllegalStateException e) {
            return null;
        }
    }
}
