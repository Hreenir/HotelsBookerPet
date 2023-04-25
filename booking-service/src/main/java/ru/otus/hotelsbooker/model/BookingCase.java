package ru.otus.hotelsbooker.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class BookingCase {

    //поле заезда(хранит дату заезда)
    private LocalDate checkInDate;
    //поле выезда(хранит дату выезда)
    private LocalDate checkOutDate;
    private String Hotel;

    //поле номера комнаты(хранит номер комнаты)
    private int roomNumber;

    //(Возможно нужен инт, так как клиенты будут искаться по ID???)
    private String client;

    //поле стоимость брони(хранит стоимость)
    private static double priceByDay;


    //рассчет стоимости брони
    double costCalculate() {
        double costRoom = 150.0; //стоимость комнаты
        int days = 10; // кол-во дней (тут возможно есть какой-то метод, который считает дни?)
        return costRoom * days;
    }

}
