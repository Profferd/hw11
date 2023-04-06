package com.intern.practice.hw10.repo;

import com.intern.practice.hw10.entity.Role;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
public class RoleRepo {

    private static final Map<String, Role> ROLE_MAP = Stream.of(
            new Role("USER", List.of()),
            new Role("ADMIN", List.of("USER_MANAGEMENT", "BOOK_MANAGEMENT"))
    ).collect(Collectors.toUnmodifiableMap(Role::getId, Function.identity()));

    public Optional<Role> getRole(String id) {
        return Optional.ofNullable(ROLE_MAP.get(id));
    }
}
