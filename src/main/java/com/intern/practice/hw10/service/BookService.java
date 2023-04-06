package com.intern.practice.hw10.service;

import com.intern.practice.hw10.dto.BookDetailInfo;
import com.intern.practice.hw10.dto.BookDto;

public interface BookService {
    String save(BookDto bookDto);

    BookDetailInfo readById(String id);

    String getTextOfBookById(String id);
}
