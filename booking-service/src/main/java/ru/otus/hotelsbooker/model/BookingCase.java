package ru.otus.hotelsbooker.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Calendar;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(name = "bookings")
public class BookingCase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tg_user_id")
    private TgUser tgUser;

    @ManyToOne
    @JoinColumn(name = "local_room_id")
    private LocalRoom localRoom;

    @Column(name = "check_in_date")
    private Calendar checkInDate;

    @Column(name = "check_out_date")
    private Calendar checkOutDate;

    @Column(name = "price_by_day")
    private BigDecimal priceByDay;

}
