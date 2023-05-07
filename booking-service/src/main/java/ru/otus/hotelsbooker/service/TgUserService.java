package ru.otus.hotelsbooker.service;

import jakarta.transaction.Transactional;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.dto.RoleDto;
import ru.otus.dto.TgUserDto;
import ru.otus.hotelsbooker.model.Role;
import ru.otus.hotelsbooker.model.TgUser;
import ru.otus.hotelsbooker.repository.TgUserJpaRepository;

@Service
@Getter
@Transactional
public class TgUserService {
    private TgUserJpaRepository tgUserJpaRepository;
    @Autowired
    public TgUserService(TgUserJpaRepository tgUserJpaRepository){
        this.tgUserJpaRepository = tgUserJpaRepository;
    }
    public TgUserDto createTgUser(TgUserDto tgUserDto ){
        Role role = Role.builder()
                .id(tgUserDto.getRole().getId())
                .name(tgUserDto.getRole().getName())
                .build();
        TgUser tgUser = tgUserJpaRepository.save(TgUser.builder()
                .id(tgUserDto.getId())
                .role(role)
                .build());
        RoleDto roleDto = RoleDto.builder()
                .id(tgUser.getRole().getId())
                .name(tgUser.getRole().getName())
                .build();


        return TgUserDto.builder()
                .id(tgUser.getId())
                .role(roleDto)
                .build();
    }
}
