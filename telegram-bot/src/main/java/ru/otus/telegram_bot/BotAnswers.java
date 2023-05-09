package ru.otus.telegram_bot;

import lombok.Getter;

public class BotAnswers {
    @Getter
    private final static String HOTEL_COMMANDS =
            """
                    You can use commands:
                    /addhotel {hotelDto}
                    /updatehotel {hotelDto}
                    /add {roomDto}
                    /add {localRoomDto}
                    /delete {localRoomDto}
                    """;
    @Getter
    private final static String VISITOR_COMMANDS =
            """
                    You can use commands:
                    /search {hotelDto}
                    """;
}
