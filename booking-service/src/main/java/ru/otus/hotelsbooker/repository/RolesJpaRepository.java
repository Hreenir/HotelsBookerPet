package ru.otus.hotelsbooker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.otus.hotelsbooker.model.Role;

import java.util.Set;

public interface RolesJpaRepository extends JpaRepository<Role, Long> {
    @Query(nativeQuery = true, value =
            "SELECT r.id, r.name from users_to_roles\n" +
            "join roles r on users_to_roles.role_id = r.id\n" +
            "where user_id = :userId")
    public Set<Role> getRolesByUserId(@Param("userId")Long userId);
}
