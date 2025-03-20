package ru.kata.spring.boot_security.demo.service;

import jakarta.annotation.PostConstruct;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void save(User entity) {
        userRepository.save(entity);
    }

    public void saveAll(Iterable<User> entities) {
        userRepository.saveAll(entities);
    }

    public Optional<User> findById(Long aLong) {
        return userRepository.findById(aLong);
    }

    public Iterable<User> findAll() {
        return userRepository.findAll();
    }

    public void deleteById(Long aLong) {
        userRepository.deleteById(aLong);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

    //Add admin for first login
    @PostConstruct
    public void init() {
        userRepository.save(User.builder()
                        .username("admin")
                        .password("{noop}admin")
                        .authority(Role.USER)
                        .authority(Role.ADMIN)
                        .build()
        );
        userRepository.save(User.builder()
                .username("user")
                .password("{noop}user")
                .authority(Role.USER)
                .build()
        );
    }
}
