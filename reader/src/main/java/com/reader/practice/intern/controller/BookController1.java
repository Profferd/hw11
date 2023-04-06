package com.reader.practice.intern.controller;

import com.intern.practice.hw10.dto.BookDetailInfo;
import com.reader.practice.intern.BookClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import com.intern.practice.hw10.entity.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Base64;
import java.util.List;

@Controller
public class BookController1 {
    private String email;
    private String pass;

    @Autowired
    private BookClient bookClient;

    @GetMapping("/myBook")
    public String home(Model model) {
        String authHeader = create(email, pass);

        try {
            List<BookDetailInfo> bookDetailInfos = bookClient.read(authHeader);
            model.addAttribute("myBook", bookDetailInfos);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }

        return "myBook";
    }

    @GetMapping("/login")
    public String getLoginPage(){
        return "login";
    }

    @PostMapping("/login")
    public String login(Model model, String email, String pass) {
        String authHeader = create(email, pass);

        try {
            bookClient.read(authHeader);

            this.email = email;
            this.pass = pass;

            return "redirect:/myBook";
        } catch (Exception e) {
            model.addAttribute("error" + e.getMessage());
        }

        return "/login";
    }

    @GetMapping("/myBook/{id}")
    public String getText(@PathVariable String id, Model model) {
        String authHeader = create(email, pass);

        try {
            String text = bookClient.readText(id, authHeader);
            model.addAttribute("text", text);
        } catch (Exception e) {
            model.addAttribute("error" + e.getMessage());
        }

        return "readText";
    }

    private String create(String email, String pass) {
        byte[] encodeBytes = Base64.getEncoder().encode((email + ":" + pass).getBytes());
        return "Basic " + new String(encodeBytes);
    }
}
