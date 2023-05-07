package ru.otus.telegram_bot.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;

import java.util.List;

public interface BotCommands {

    List<BotCommand> LIST_OF_COMMANDS = List.of(
            new BotCommand("/setrolehotel", "I'm hotel"),
            new BotCommand("/setrolevisitor", "I'm visitor")
    );
}