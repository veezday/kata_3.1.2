package ru.kata.spring.boot_security.demo.controller;

import jakarta.annotation.security.RolesAllowed;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getUsers(@RequestParam(required = false) Long id, Model model) {
        User user = null;
        if (id != null) {
            user = userService.findById(id).orElse(null);
        }

        model.addAttribute("users", user == null ? userService.findAll() : user);
        return "admin";
    }

    @PostMapping("/init")
    public RedirectView init() {
        userService.saveAll(List.of(
                User.builder()
                        .username("John")
                        .surname("Doe")
                        .password("{noop}admin")
                        .email("john@doe.com")
                        .authority(Role.USER)
                        .authority(Role.ADMIN)
                        .build(),
                User.builder()
                        .username("Username")
                        .surname("UserLastName")
                        .password("{noop}123")
                        .authority(Role.USER)
                        .build(),
                User.builder()
                        .username("Password")
                        .surname("Password")
                        .password("{noop}Password")
                        .email("password@email.com")
                        .authority(Role.USER)
                        .authority(Role.ADMIN)
                        .build(),
                User.builder()
                        .username("Welcome")
                        .surname("ToSite")
                        .password("{noop}Kata")
                        .email("academy@site.ru")
                        .authority(Role.USER)
                        .build(),
                User.builder()
                        .username("Student")
                        .surname("NeverMin")
                        .password("{noop}placeholder")
                        .email("student@email.com")
                        .authority(Role.USER)
                        .build()
        ));
        return new RedirectView("/admin");
    }

    @PostMapping("/create")
    public RedirectView addUser(@RequestParam String username,
                                @RequestParam(required = false) String surname,
                                @RequestParam String password,
                                @RequestParam(required = false) String email,
                                @RequestParam List<Role> authorities) {
        userService.save(User.builder()
                .username(username)
                .surname(surname)
                .password(password)
                .email(email)
                .authorities(authorities)
                .build());
        return new RedirectView("/admin");
    }

    @PostMapping("/update")
    public RedirectView updateUser(@RequestParam Long id,
                                   @RequestParam String name,
                                   @RequestParam(required = false) String surname,
                                   @RequestParam String password,
                                   @RequestParam(required = false) String email,
                                   @RequestParam List<Role> authorities) {
        User user = userService.findById(id).orElse(null);
        if (user != null) {
            user.setUsername(name);
            user.setSurname(surname);
            user.setPassword(password);
            user.setEmail(email);
            user.setAuthorities(authorities);
            userService.save(user);
        }

        return new RedirectView("/admin");
    }

    @PostMapping("/delete")
    public RedirectView deleteUser(@RequestParam Long id) {
        userService.deleteById(id);
        return new RedirectView("/admin");
    }
}
