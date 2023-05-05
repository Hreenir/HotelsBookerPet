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
    private String name;
    private String city;
    private String country;
}
