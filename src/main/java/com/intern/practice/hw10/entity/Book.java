package com.intern.practice.hw10.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document("books")
@Getter
@Setter
@Builder
public class Book {
    @Id
    private String id;
    private String title;
    private String author;
    private String publisher;
    private String isbn;
    private String publishingYear;
    private String text;
}
