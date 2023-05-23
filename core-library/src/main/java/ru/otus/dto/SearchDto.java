package ru.otus.dto;

import lombok.*;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SearchDto {
    private long id;
    private String name;
    private String city;
    private String country;
}
