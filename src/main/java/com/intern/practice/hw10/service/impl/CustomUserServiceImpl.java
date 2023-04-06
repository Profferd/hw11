package com.intern.practice.hw10.service.impl;

import com.intern.practice.hw10.dto.UserDto;
import com.intern.practice.hw10.entity.Role;
import com.intern.practice.hw10.entity.Users;
import com.intern.practice.hw10.repo.RoleRepo;
import com.intern.practice.hw10.repo.UserRepo;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import com.intern.practice.hw10.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class CustomUserServiceImpl implements UserDetailsService {

    private final RoleRepo roleRepo;
    private final UserRepo userRepo;

    @Autowired
    public CustomUserServiceImpl(RoleRepo roleRepo, UserRepo userRepo) {
        this.roleRepo = roleRepo;
        this.userRepo = userRepo;
    }

    @PostConstruct
    public void init() {
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

        Users regularUser = Users.builder()
                .email("joe_biden@somemail.com")
                .password(passwordEncoder.encode("password"))
                .role("USER")
                .enabled(true)
                .booksIds(List.of())
                .build();

        userRepo.save(regularUser);

        Users admin = Users.builder()
                .email("admin@admin.com")
                .password(passwordEncoder.encode("password"))
                .role("ADMIN")
                .enabled(true)
                .booksIds(List.of())
                .build();

        userRepo.save(admin);
    }

    @PreDestroy
    public void preDestroy() {
        userRepo.deleteByEmail("joe_biden@somemail.com");
        userRepo.deleteByEmail("admin@admin.com");
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<Users> users = userRepo.findByEmail(username);

        if (users.isEmpty()) {
            throw new UsernameNotFoundException("No such user with email - " + username);
        }

        return convertToUserDetails(users.get(0));

    }

    private UserDetails convertToUserDetails(Users user1) {
        return User.withUsername(user1.getEmail())
                .password(user1.getPassword())
                .authorities(collectAuthorities(user1.getRole()))
                .disabled(!user1.isEnabled())
                .build();
    }

    private List<GrantedAuthority> collectAuthorities(String role) {
        return roleRepo.getRole(role)
                .map(Role::getPrivs)
                .stream().flatMap(Collection::stream)
                .map(priv -> (GrantedAuthority)new SimpleGrantedAuthority("PRIV_" + priv))
                .toList();
    }


}
