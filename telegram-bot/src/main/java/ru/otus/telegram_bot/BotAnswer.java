package ru.otus.telegram_bot;

import lombok.Getter;

public class BotAnswer {
    public final static String HOTEL_COMMANDS =
            """
                    You can use commands:
                    /addhotel {hotelDto}
                    /updatehotel {hotelDto}
                    /addroom {roomDto}
                    /addlocalroom {localRoomDto}
                    /disablelocalroom {localRoomDto}
                    """;

    public final static String VISITOR_COMMANDS =
            """
                    You can use commands:
                    /searchbycity {SearchDto}
                    """;

    public final static String INCORRECT_INPUT =
            "Incorrect input, try again.";
    public final static String INCORRECT_HOTEL_ID =
            "Hotel not found!";
    public final static String INCORRECT_LOCAL_ROOM_ID =
            "LocalRoom not found!";
    public final static String INCORRECT_ROOM_ID =
            "LocalRoom not found!";
}
