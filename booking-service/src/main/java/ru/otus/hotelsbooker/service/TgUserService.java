package ru.otus.hotelsbooker.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.dto.TgUserDto;
import ru.otus.hotelsbooker.exception.ResourceNotFoundException;
import ru.otus.hotelsbooker.mapper.TgUserMapper;
import ru.otus.hotelsbooker.model.TgUser;
import ru.otus.hotelsbooker.repository.RolesRepository;
import ru.otus.hotelsbooker.repository.TgUserRepository;

@Service
@RequiredArgsConstructor
public class TgUserService {
    private final TgUserRepository tgUserRepository;
    private final RolesRepository rolesRepository;
    @Transactional
    public TgUser createTgUser(TgUserDto tgUserDto ){
        TgUser tgUser = TgUserMapper.mapToTgUser(tgUserDto);
        String roleName = tgUserDto.getRole().getName();
        tgUser.setRole(rolesRepository.getRoleByName(roleName));
        return tgUserRepository.save(tgUser);
    }
    public TgUser getUserById(long tgUserId){
        if (!tgUserRepository.existsById(tgUserId)) {
            throw new ResourceNotFoundException("tgUser with id=" + tgUserId + " not found!");
        }
        return tgUserRepository.findTgUserById(tgUserId);

    }
}
