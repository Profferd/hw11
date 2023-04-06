package com.intern.practice.hw10.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class UserDto {
    private String email;
    private String password;
    private String role;
    private boolean enabled;
    private List<String> booksIds;
}
