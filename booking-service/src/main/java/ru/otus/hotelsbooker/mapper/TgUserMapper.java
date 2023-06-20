package ru.otus.hotelsbooker.mapper;

import ru.otus.dto.TgUserDto;
import ru.otus.hotelsbooker.model.TgUser;

public class TgUserMapper {
    public static TgUser mapToTgUser(TgUserDto tgUserDto) {
        return TgUser.builder()
                .id(tgUserDto.getId())
                .role(RoleMapper.mapToRole(tgUserDto.getRole()))
                .build();
    }

    public static TgUserDto mapToTgUserDto(TgUser tgUser) {
        return TgUserDto.builder()
                .id(tgUser.getId())
                .role(RoleMapper.mapToRoleDto(tgUser.getRole()))
                .build();
    }
}
