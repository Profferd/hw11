package com.intern.practice.hw10.repo;

import com.intern.practice.hw10.entity.Users;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends MongoRepository<Users, String> {

    Optional<Users> findById(String id);

    List<Users> findByEmail(String email);

    List<Users> deleteByEmail(String email);
}
