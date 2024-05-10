package com.example.libadmin.controller;

import com.example.libadmin.domain.Book;
import com.example.libadmin.domain.User;
import com.example.libadmin.repository.BookRepository;
import com.example.libadmin.repository.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class BookmarkController {
    private BookRepository bookRepository;
    private UserRepository userRepository;

    public BookmarkController(BookRepository bookRepository, UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/bookmark/{bookID}")
    public void bookmark(@PathVariable("bookID") Long bookID, Principal principal) {
        Book book = bookRepository.findById(bookID).orElse(null);

            System.out.println(principal.getName());
            System.out.println(book);
            User user = userRepository.findByUsername(principal.getName()).orElse(null);
            // since only logged-in users are have bookmark functionality, null check is not necessary
            user.addFavorite(book);
            userRepository.save(user);

            //voteService.save(vote);
    }
}
