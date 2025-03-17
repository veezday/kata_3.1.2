package ru.kata.spring.boot_security.demo.model;

import jakarta.persistence.*;

import lombok.*;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table
public class User implements UserDetails {
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String surname;
    private String password;
    private String email;
    @Singular
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private List<Role> authorities;
}
