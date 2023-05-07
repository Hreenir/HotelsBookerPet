package ru.otus.hotelsbooker.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.otus.hotelsbooker.model.Role;
import ru.otus.hotelsbooker.model.User;
import ru.otus.hotelsbooker.repository.RolesJpaRepository;
import ru.otus.hotelsbooker.repository.UsersJpaRepository;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class RegistrationService {
    private final PasswordEncoder passwordEncoder;
    private final UsersJpaRepository usersJpaRepository;
    private final RolesJpaRepository rolesJpaRepository;

    private Role hotelRole;

    @PostConstruct
    public void init() {
        hotelRole = rolesJpaRepository.save(Role.builder()
                .name("ROLE_HOTEL")
                .build());
        rolesJpaRepository.save(Role.builder()
                .name("ROLE_VISITOR")
                .build());
        register("user", "user");
    }

    public void register(String username, String password) {
        if (usersJpaRepository.findByUsername(username) == null) {
            String encodedPassword = passwordEncoder.encode(password);
            usersJpaRepository.save(User.builder()
                    .username("user")
                    .password(encodedPassword)
                    .enabled(true)
                    .roles(Set.of(hotelRole))
                    .build());
        }
    }

    public void addRole(String name) {
        rolesJpaRepository.save(Role.builder()
                .name(name)
                .build());
    }
}
