package com.intern.practice.hw10.controller;

import com.intern.practice.hw10.dto.UserDto;
import com.intern.practice.hw10.entity.Users;
import com.intern.practice.hw10.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('PRIV_USER_MANAGEMENT')")
    public String addUser(@RequestBody UserDto userSaveDto) {
        if (userSaveDto.getRole().equals("ADMIN")) {
            return "You can't create ADMIN users!";
        }

        if (userService.exists(userSaveDto.getEmail())) {
            return "User with email " + userSaveDto.getEmail() + " already exists!";
        }

        return userService.save(userSaveDto);
    }

    @PostMapping("/{userId}/addBooks")
    @PreAuthorize("hasAuthority('PRIV_USER_MANAGEMENT')")
    public String addBooksToUser(@PathVariable String userId, @RequestBody List<String> booksIds) {
        try {
            Users user = userService.readById(userId);

            // додаємо нові книги до вже наявних
            user.setBooksIds(Stream.concat(user.getBooksIds().stream(), booksIds.stream()).toList());

            return userService.update(user);
        } catch (NoSuchElementException e) {
            return e.getMessage();
        }
    }
}
