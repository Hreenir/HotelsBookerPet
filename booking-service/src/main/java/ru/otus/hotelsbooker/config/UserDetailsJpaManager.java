package ru.otus.hotelsbooker.config;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import ru.otus.hotelsbooker.model.Role;
import ru.otus.hotelsbooker.repository.RolesJpaRepository;
import ru.otus.hotelsbooker.repository.UsersJpaRepository;

import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class UserDetailsJpaManager implements UserDetailsManager {
    private final UsersJpaRepository usersJpaRepository;
    private final RolesJpaRepository rolesJpaRepository;

    @Override
    public void createUser(UserDetails user) {

    }

    @Override
    public void updateUser(UserDetails user) {

    }

    @Override
    public void deleteUser(String username) {

    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {

    }

    @Override
    public boolean userExists(String username) {
        return false;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ru.otus.hotelsbooker.model.User user = usersJpaRepository.findByUsername(username);
        Set<Role> roles = rolesJpaRepository.getRolesByUserId(user.getId());
        return new User(user.getUsername(), user.getPassword(), mapRolesToGrantedAuthority(roles));
    }
    private Set<GrantedAuthority> mapRolesToGrantedAuthority(Set<Role> roles){
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        roles.forEach(role ->
        {GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role.getName());
            grantedAuthorities.add(grantedAuthority);
        });

        return grantedAuthorities;
    }
}
