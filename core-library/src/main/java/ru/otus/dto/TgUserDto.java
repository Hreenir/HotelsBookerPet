package ru.otus.dto;

import lombok.*;



@Getter
@Setter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TgUserDto {
    private long id;
    private RoleDto role;

}
