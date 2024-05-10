package com.example.libadmin.controller;

import com.example.libadmin.repository.BookRepository;
import com.example.libadmin.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@Controller
public class UserController {

    private BookRepository bookRepository;
    private UserRepository userRepository;

    public UserController(BookRepository bookRepository, UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }


    @GetMapping("/account")
    public String account(Model model, Principal principal) {
        model.addAttribute("bookmarked", userRepository.findByUsername(principal.getName()).orElse(null).getFavorites());
        return "account";
    }
}
