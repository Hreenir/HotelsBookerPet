package ru.otus.telegram_bot;


public class BotAnswer {
    public final static String HOTEL_COMMANDS =
            """
                    You can use commands:
                    /addHotel {hotelDto}
                    /updateHotel {hotelDto}
                    /addRoom {roomDto}
                    /addLocalRoom {localRoomDto}
                    /disableLocalRoom {localRoomDto}
                    """;

    public final static String VISITOR_COMMANDS =
            """
                    You can use commands:
                    /searchByCity {SearchDto}
                    /createBooking {CreateBookingDto}
                    /getMyBookings
                    /cancelBooking {CreateBookingDto}
                    """;

    public final static String INCORRECT_INPUT =
            "Incorrect input, try again.";
    public final static String INCORRECT_HOTEL_ID =
            "Hotel not found!";
    public final static String INCORRECT_LOCAL_ROOM_ID =
            "LocalRoom not found!";
    public final static String INCORRECT_ROOM_ID =
            "LocalRoom not found!";
    public final static String INCORRECT_BOOKING_ID =
            "BOOKING not found!";
}
