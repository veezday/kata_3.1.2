package ru.kata.spring.boot_security.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/get")
    public ResponseEntity<Map<String, Object>> getUsers(@AuthenticationPrincipal User admin) {
        Map<String, Object> entity = new HashMap<>();
        entity.put("admin", admin);
        entity.put("users", userService.findAll());

        return ResponseEntity.ok(entity);
    }

    @PostMapping
    public ResponseEntity<Void> addUser(@RequestBody User user) {
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        userService.save(user);
        return ResponseEntity.created(location).build();
    }

    @PutMapping
    public ResponseEntity<Void> updateUser(@RequestBody User user) {
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();

        if (userService.findById(user.getId()).isPresent()) {
            userService.save(user);
        }

        return ResponseEntity.ok().location(location).build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteUser(@RequestBody User user) {
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        userService.deleteById(user.getId());
        return ResponseEntity.noContent().location(location).build();
    }
}
