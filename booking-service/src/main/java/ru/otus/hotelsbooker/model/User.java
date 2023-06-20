package ru.otus.hotelsbooker.model;

import jakarta.persistence.*;

import lombok.*;

import java.util.Set;

@Data
@Builder
@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Exclude
    private Long id;
    private String username;
    private String password;
    private boolean enabled;
    @ManyToMany
    @JoinTable(name = "users_to_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;
}
