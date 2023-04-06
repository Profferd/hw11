package com.intern.practice.hw10.service.impl;

import com.intern.practice.hw10.dto.BookDetailInfo;
import com.intern.practice.hw10.dto.BookDto;
import com.intern.practice.hw10.entity.Book;
import com.intern.practice.hw10.repo.BookRepo;
import com.intern.practice.hw10.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepo bookRepo;

    @Autowired
    public BookServiceImpl(BookRepo bookRepo) {
        this.bookRepo = bookRepo;
    }

    @Override
    public String save(BookDto bookDto) {
        Book book = convertToBook(bookDto);
        return bookRepo.insert(book).getId();
    }

    @Override
    public BookDetailInfo readById(String id) {
        Book book = bookRepo.findById(id).orElseThrow(() -> new NoSuchElementException("Book id = " + id + " not found"));

        return convertToBookDetailInfo(book);
    }

    @Override
    public String getTextOfBookById(String id) {
        Book book = bookRepo.findById(id).orElseThrow(() -> new NoSuchElementException("Book id = " + id + " not found"));

        return book.getText();
    }

    private Book convertToBook(BookDto bookDto) {
        return Book.builder()
                .title(bookDto.getTitle())
                .author(bookDto.getAuthor())
                .publisher(bookDto.getPublisher())
                .isbn(bookDto.getIsbn())
                .publishingYear(bookDto.getPublishingYear())
                .text(bookDto.getText()).build();
    }

    private BookDetailInfo convertToBookDetailInfo(Book book) {

        return BookDetailInfo.builder()
                .title(book.getTitle())
                .author(book.getAuthor())
                .publisher(book.getPublisher())
                .isbn(book.getIsbn())
                .publishingYear(book.getPublishingYear()).build();
    }
}
