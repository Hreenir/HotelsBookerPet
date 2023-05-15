package ru.otus.telegram_bot;

import lombok.Getter;

public class BotAnswer {
    @Getter
    private final static String HOTEL_COMMANDS =
            """
                    You can use commands:
                    /addhotel {hotelDto}
                    /updatehotel (hotelId) {hotelDto}
                    /addroom (hotelId) {roomDto}
                    /addlocalroom (roomId) {localRoomDto}
                    /disablelocalroom (localRoomDtoId)
                    """;
    @Getter
    private final static String VISITOR_COMMANDS =
            """
                    You can use commands:
                    /searchbycity {SearchDto}
                    """;

    public final static String INCORRECT_INPUT =
            "Incorrect input, try again.";
}
