package ru.otus.hotelsbooker.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BookingCaseTest {

    @Test
    void getCheckInDate() {
            LocalDate checkInDate = LocalDate.of(2023, 4, 1);
            BookingCase booking = new BookingCase(checkInDate, LocalDate.of(2023, 4, 14), "Hilton", 111, "Denis");
            assertEquals(checkInDate, booking.getCheckInDate());
        }

    @Test
    void getCheckOutDate() {
        LocalDate checkOutDate = LocalDate.of(2023, 4, 1);
        BookingCase booking = new BookingCase(checkOutDate, LocalDate.of(2023, 4, 1), "Hilton", 111, "Denis");
        assertEquals(checkOutDate, booking.getCheckOutDate());

    }

    @Test
    void getHotel() {
        String hotel = "Hilton";
        BookingCase booking = new BookingCase(LocalDate.of(2023, 4, 1), LocalDate.of(2023, 4, 14), hotel, 111, "Denis");
        assertEquals(hotel, booking.getHotel());
    }

    @Test
    void getRoomNumber() {
        int roomNumber = 111;
        BookingCase booking = new BookingCase(LocalDate.of(2023, 4, 1), LocalDate.of(2023, 4, 14), "Hilton", 111, "Denis");
        assertEquals(roomNumber, booking.getRoomNumber());

    }

    @Test
    void getClient() {
        String client = "Denis";
        BookingCase booking = new BookingCase(LocalDate.of(2023, 4, 1), LocalDate.of(2023, 4, 14), "Hilton", 111, client);
        assertEquals(client, booking.getClient());

    }

    @Test
    void setCheckInDate() {
        LocalDate checkInDate = LocalDate.of(2023, 4, 1);
        BookingCase booking = new BookingCase(LocalDate.of(2023, 4, 2), LocalDate.of(2023, 4, 14), "Hilton", 111, "Denis");
        booking.setCheckInDate(checkInDate);
        assertEquals(checkInDate, booking.getCheckInDate());
    }

    @Test
    void setCheckOutDate() {
        LocalDate checkOutDate = LocalDate.of(2023, 4, 15);
        BookingCase booking = new BookingCase(LocalDate.of(2023, 4, 1), LocalDate.of(2023, 4, 14), "Hilton", 111, "Denis");
        booking.setCheckOutDate(checkOutDate);
        assertEquals(checkOutDate, booking.getCheckOutDate());
    }

    @Test
    void setHotel() {
        String hotel = "Marriott";
        BookingCase booking = new BookingCase(LocalDate.of(2023, 4, 1), LocalDate.of(2023, 4, 14), "Hilton", 111, "Denis");
        booking.setHotel(hotel);
        assertEquals(hotel, booking.getHotel());
    }

    @Test
    void setRoomNumber() {
        int roomNumber = 112;
        BookingCase booking = new BookingCase(LocalDate.of(2023, 4, 1), LocalDate.of(2023, 4, 14), "Hilton", 111, "Denis");
        booking.setRoomNumber(roomNumber);
        assertEquals(roomNumber, booking.getRoomNumber());
    }

    @Test
    void setClient() {
        String client = "Yurik";
        BookingCase booking = new BookingCase(LocalDate.of(2023, 4, 1), LocalDate.of(2023, 4, 14), "Hilton", 111, "Denis");
        booking.setClient(client);
        assertEquals(client, booking.getClient());
    }

    @Test
    void getpriceByDay(){
        BookingCase booking = new BookingCase(LocalDate.of(2023, 4, 1), LocalDate.of(2023, 4, 14), "Hilton", 111, "Denis");
        assertEquals(1500.0, booking.costCalculate());
    }
}