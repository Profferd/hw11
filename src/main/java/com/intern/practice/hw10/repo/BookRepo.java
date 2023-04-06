package com.intern.practice.hw10.repo;

import com.intern.practice.hw10.entity.Book;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BookRepo extends MongoRepository<Book, String> {
}
