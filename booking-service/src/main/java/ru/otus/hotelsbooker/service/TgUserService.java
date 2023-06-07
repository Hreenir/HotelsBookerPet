package ru.otus.hotelsbooker.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.dto.TgUserDto;
import ru.otus.hotelsbooker.exception.HotelNotFoundException;
import ru.otus.hotelsbooker.mapper.TgUserMapper;
import ru.otus.hotelsbooker.model.TgUser;
import ru.otus.hotelsbooker.repository.RolesJpaRepository;
import ru.otus.hotelsbooker.repository.TgUserJpaRepository;

@Service
@RequiredArgsConstructor
public class TgUserService {
    private final TgUserJpaRepository tgUserJpaRepository;
    private final RolesJpaRepository rolesJpaRepository;
    @Transactional
    public TgUserDto createTgUser(TgUserDto tgUserDto ){
        TgUser tgUser = TgUserMapper.mapToTgUser(tgUserDto);
        String roleName = tgUserDto.getRole().getName();
        tgUser.setRole(rolesJpaRepository.getRoleByName(roleName));
        TgUser savedTgUser = tgUserJpaRepository.save(tgUser);
        return TgUserMapper.mapToTgUserDto(savedTgUser);
    }
    public TgUserDto getUserById(long tgUserId){
        TgUser tgUser = tgUserJpaRepository.findTgUserById(tgUserId);
        if (tgUser == null) {
            throw new HotelNotFoundException("tgUser with id=" + tgUserId + " not found!");
        }
        return TgUserMapper.mapToTgUserDto(tgUser);

    }
}
