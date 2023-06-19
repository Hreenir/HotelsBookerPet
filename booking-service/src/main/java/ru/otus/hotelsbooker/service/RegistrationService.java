package ru.otus.hotelsbooker.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.otus.hotelsbooker.model.Role;
import ru.otus.hotelsbooker.model.User;
import ru.otus.hotelsbooker.repository.RolesRepository;
import ru.otus.hotelsbooker.repository.UsersRepository;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class RegistrationService {
    private final PasswordEncoder passwordEncoder;
    private final UsersRepository usersRepository;
    private final RolesRepository rolesRepository;

    @PostConstruct
    public void init() {
        if (rolesRepository.getRoleByName("ROLE_HOTEL") == null) {
        rolesRepository.save(Role.builder()
                .name("ROLE_HOTEL")
                .build());
        register("user", "user");
        }
        if (rolesRepository.getRoleByName("ROLE_VISITOR") == null){
            rolesRepository.save(Role.builder()
                    .name("ROLE_VISITOR")
                    .build());
        }
    }

    public void register(String username, String password) {
        if (usersRepository.findByUsername(username) == null) {
            String encodedPassword = passwordEncoder.encode(password);
            usersRepository.save(User.builder()
                    .username(username)
                    .password(encodedPassword)
                    .enabled(true)
                    .roles(Set.of(rolesRepository.getRoleByName("ROLE_HOTEL")))
                    .build());
        }
    }

    public void addRole(String name) {
        rolesRepository.save(Role.builder()
                .name(name)
                .build());
    }
}
