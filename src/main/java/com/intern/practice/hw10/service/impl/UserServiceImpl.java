package com.intern.practice.hw10.service.impl;

import com.intern.practice.hw10.dto.UserDto;
import com.intern.practice.hw10.entity.Users;
import com.intern.practice.hw10.repo.UserRepo;
import com.intern.practice.hw10.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    @Autowired
    public UserServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public String save(UserDto userDto) {
        Users user = convertToUser(userDto);
        return userRepo.insert(user).getId();
    }

    @Override
    public String update(Users user) {
        return userRepo.save(user).getId();
    }

    @Override
    public Users readById(String id) {
        return userRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User id = " + id + " not found"));
    }

    @Override
    public Users readByEmail(String email) {
        if (!exists(email)) {
            throw new NoSuchElementException("User id = " + email + " not found");
        }

        return userRepo.findByEmail(email).get(0);
    }

    @Override
    public boolean exists(String email) {
        return !userRepo.findByEmail(email).isEmpty();
    }

    private Users convertToUser(UserDto userDto) {
        return Users.builder()
                .email(userDto.getEmail())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .role(userDto.getRole())
                .enabled(userDto.isEnabled())
                .booksIds(userDto.getBooksIds())
                .build();
    }
}
