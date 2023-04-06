package com.intern.practice.hw10.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@Document("users")
@Builder
public class Users {
    @Id
    private String id;
    private String email;
    private String password;
    private String role;
    private boolean enabled;
    private List<String> booksIds;
}
