package com.intern.practice.hw10.service;

import com.intern.practice.hw10.dto.UserDto;
import com.intern.practice.hw10.entity.Users;

public interface UserService {
    String save(UserDto userDto);

    String update(Users user);

    Users readById(String id);

    Users readByEmail(String email);

    boolean exists(String email);
}
