package com.intern.practice.hw10.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BookDetailInfo {
    private String id;
    private String title;
    private String author;
    private String publisher;
    private String isbn;
    private String publishingYear;
}
