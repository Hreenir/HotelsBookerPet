package ru.otus.hotelsbooker.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Builder
@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Exclude
    private Long id;
    private String name;
    private int capacity;
    @Column(name = "pricebyday")
    private BigDecimal priceByDay;
    @ManyToOne
    @JoinColumn(name = "hotel")
    private Hotel hotel;
    @OneToMany
    @JoinColumn(name = "room")
    private List<LocalRoom> rooms = new ArrayList<>();


}
