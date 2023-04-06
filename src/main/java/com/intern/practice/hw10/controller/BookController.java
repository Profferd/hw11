package com.intern.practice.hw10.controller;

import com.intern.practice.hw10.dto.BookDetailInfo;
import com.intern.practice.hw10.dto.BookDto;
import com.intern.practice.hw10.entity.Users;
import com.intern.practice.hw10.service.BookService;
import com.intern.practice.hw10.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;
    private final UserService userService;

    @Autowired
    public BookController(BookService bookService, UserService userService) {
        this.bookService = bookService;
        this.userService = userService;
    }

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('PRIV_BOOK_MANAGEMENT')")
    public String addBook(@RequestBody BookDto bookSaveDto) {
        return bookService.save(bookSaveDto);
    }

    @GetMapping("/myBooks")
    public List<BookDetailInfo> getMyBooks() {
        Users user = userService.readByEmail(getUserEmail());

        List<BookDetailInfo> books = new ArrayList<>();

        for (String id : user.getBooksIds()) {
            try {
                books.add(bookService.readById(id));
            } catch (NoSuchElementException e) {
                System.err.println(e.getMessage());
            }
        }
        return books;
    }

    @GetMapping("/myBooks/{id}/text")
    public String getTextOfCertainBook(@PathVariable String id) {
        Users user = userService.readByEmail(getUserEmail());

        try {
            if (user.getBooksIds().contains(id)) {
                return bookService.getTextOfBookById(id);
            } else {
                return "This book doesn't belong to you or doesn't exist";
            }
        } catch (NoSuchElementException e) {
            return e.getMessage();
        }
    }

    private String getUserEmail() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        Object principal = authentication.getPrincipal();

        if (principal instanceof User) {
            return ((User) principal).getUsername();
        } else {
            return "Something went wrong";
        }
    }
}
