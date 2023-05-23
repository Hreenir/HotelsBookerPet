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

    @PostConstruct
    public void init() {
        if (rolesJpaRepository.getRoleByName("ROLE_HOTEL") == null) {
        rolesJpaRepository.save(Role.builder()
                .name("ROLE_HOTEL")
                .build());
        register("user", "user");
        }
        if (rolesJpaRepository.getRoleByName("ROLE_VISITOR") == null){
            rolesJpaRepository.save(Role.builder()
                    .name("ROLE_VISITOR")
                    .build());
        }
    }

    public void register(String username, String password) {
        if (usersJpaRepository.findByUsername(username) == null) {
            String encodedPassword = passwordEncoder.encode(password);
            usersJpaRepository.save(User.builder()
                    .username("user")
                    .password(encodedPassword)
                    .enabled(true)
                    .roles(Set.of(rolesJpaRepository.getRoleByName("ROLE_HOTEL")))
                    .build());
        }
    }

    public void addRole(String name) {
        rolesJpaRepository.save(Role.builder()
                .name(name)
                .build());
    }
}