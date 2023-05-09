package ru.otus.telegram_bot.buttons;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

public class Buttons {
    private static final InlineKeyboardButton I_AM_HOTEL_BUTTON = new InlineKeyboardButton("I'm hotel");
    private static final InlineKeyboardButton I_AM_VISITOR_BUTTON = new InlineKeyboardButton("I'm visitor");

    public static InlineKeyboardMarkup showSelectRoleButtons() {
        I_AM_HOTEL_BUTTON.setCallbackData("/setrolehotel");
        I_AM_VISITOR_BUTTON.setCallbackData("/setrolevisitor");

        List<InlineKeyboardButton> rowInline = List.of(I_AM_HOTEL_BUTTON, I_AM_VISITOR_BUTTON);
        List<List<InlineKeyboardButton>> rowsInLine = List.of(rowInline);

        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        markupInline.setKeyboard(rowsInLine);

        return markupInline;
    }
}

