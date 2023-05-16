package ru.otus.hotelsbooker.service;

import jakarta.transaction.Transactional;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.dto.TgUserDto;
import ru.otus.hotelsbooker.exception.HotelNotFoundException;
import ru.otus.hotelsbooker.mapper.TgUserMapper;
import ru.otus.hotelsbooker.model.TgUser;
import ru.otus.hotelsbooker.repository.TgUserJpaRepository;

@Service
@Getter
@Transactional
public class TgUserService {
    private final TgUserJpaRepository tgUserJpaRepository;
    @Autowired
    public TgUserService(TgUserJpaRepository tgUserJpaRepository){
        this.tgUserJpaRepository = tgUserJpaRepository;
    }
    public TgUserDto createTgUser(TgUserDto tgUserDto ){

        TgUser tgUser = TgUserMapper.mapToTgUser(tgUserDto);
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
