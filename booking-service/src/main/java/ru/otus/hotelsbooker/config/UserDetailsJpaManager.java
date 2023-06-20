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
import ru.otus.hotelsbooker.repository.RolesRepository;
import ru.otus.hotelsbooker.repository.UsersRepository;

import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserDetailsJpaManager implements UserDetailsManager {
    private final UsersRepository usersRepository;
    private final RolesRepository rolesRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ru.otus.hotelsbooker.model.User user = usersRepository.findByUsername(username);
        Set<Role> roles = rolesRepository.getRolesByUserId(user.getId());
        return new User(user.getUsername(), user.getPassword(), mapRolesToGrantedAuthority(roles));
    }
    private Set<GrantedAuthority> mapRolesToGrantedAuthority(Set<Role> roles){
        return roles.stream().map(r -> new SimpleGrantedAuthority(r.getName())).collect(Collectors.toSet());
    }

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


}
