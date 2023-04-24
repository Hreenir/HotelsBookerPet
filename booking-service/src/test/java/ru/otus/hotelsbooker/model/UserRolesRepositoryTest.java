package ru.otus.hotelsbooker.model;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.hotelsbooker.repository.RolesJpaRepository;
import ru.otus.hotelsbooker.repository.UsersJpaRepository;

import java.util.Set;

@SpringBootTest
@Transactional
public class UserRolesRepositoryTest {
    @Autowired
    private UsersJpaRepository usersJpaRepository;
    @Autowired
    private RolesJpaRepository rolesJpaRepository;
    private Role adminRole;
    private Role userRole;
    private User adminUser;
    private User user;


    @BeforeEach
    public void prepare() {
        adminRole = rolesJpaRepository.save(Role.builder()
                .name("ROLE_ADMIN")
                .build());
        userRole = rolesJpaRepository.save(Role.builder()
                .name("ROLE_USER")
                .build());
        adminUser = usersJpaRepository.save(User.builder()
                .username("admin")
                .password("{noop}admin")
                .enabled(true)
                .roles(Set.of(adminRole, userRole))
                .build());
        user = usersJpaRepository.save(User.builder()
                .username("user")
                .password("{noop}user")
                .enabled(true)
                .roles(Set.of(userRole))
                .build());
    }

    @Test
    void testFIndUserAndRoles() {
        User actual = usersJpaRepository.findByUsername(adminUser.getUsername());
        Assertions.assertEquals(adminUser.getUsername(), actual.getUsername());
        Assertions.assertEquals(adminUser.getPassword(), actual.getPassword());
        Assertions.assertEquals(adminUser.isEnabled(), actual.isEnabled());

        Set<Role> actualRoles = rolesJpaRepository.getRolesByUserId(actual.getId());
        Assertions.assertEquals(adminUser.getRoles(), actualRoles);
    }
}
