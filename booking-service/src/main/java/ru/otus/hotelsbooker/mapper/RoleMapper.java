package ru.otus.hotelsbooker.mapper;

import ru.otus.dto.RoleDto;
import ru.otus.dto.RoomDto;
import ru.otus.hotelsbooker.model.Role;
import ru.otus.hotelsbooker.model.Room;

public class RoleMapper {
    public static Role mapToRole(RoleDto roleDto) {
        return Role.builder()
                .id(roleDto.getId())
                .name(roleDto.getName())
                .build();
    }

    public static RoleDto mapToRoleDto(Role role) {
        return RoleDto.builder()
                .id(role.getId())
                .name(role.getName())
                .build();
    }
}
