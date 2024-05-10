package com.example.libadmin.controller;

import com.example.libadmin.LastSearch;
import com.example.libadmin.domain.Book;
import com.example.libadmin.domain.Search;
import com.example.libadmin.repository.UserRepository;
import com.example.libadmin.service.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
public class BookController {
    private BookService bookService;
    private UserRepository userRepository;

    public BookController(BookService bookService, UserRepository userRepository) {
        this.bookService = bookService;
        this.userRepository = userRepository;
    }


    @GetMapping({"/", "/list"})
    public String getBookList(@RequestParam(required = false) String term,
                                        @RequestParam(required = false) String author,
                                        @RequestParam(defaultValue = "0") int pageNo,
                                        @RequestParam(defaultValue = "10") int pageSize,
                                        Model model,
                                        @ModelAttribute Book book,
                                        @ModelAttribute Search search,
                                        Principal principal,
                                        BindingResult errors) throws NoSuchFieldException {

        Page<Book> books;
        List<Book> booksTotal;
        System.out.println("author: " + author);
        System.out.println("whole book object: " + book);

        model.addAttribute("book", book);
        model.addAttribute("search", search);
        if(principal != null) {
            model.addAttribute("bookmarked", userRepository.findByUsername(principal.getName()).orElse(null).getFavorites());
        } else {
            model.addAttribute("bookmarked", null);
        }

        books = bookService.findAll(PageRequest.of(pageNo, pageSize, Sort.by("publicationDate").descending()));
        booksTotal = bookService.findAll();

        model.addAttribute("books", books.getContent());
        model.addAttribute("currentPage", books.getNumber() + 1);
        model.addAttribute("totalItems", books.getTotalElements());
        model.addAttribute("totalPages", books.getTotalPages());
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("pageNo", pageNo);

        try {
            model.addAttribute("languageGroups", bookService.groupBySearchField(booksTotal, "language"));
            model.addAttribute("accessGroups", bookService.groupBySearchField(booksTotal, "access_type"));
            model.addAttribute("departmentGroups", bookService.groupBySearchField(booksTotal, "department"));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);

        }

        return "list";
    }


    @GetMapping("/detail/{id}")
    public String showDetails(@PathVariable("id") Long id, Model model) {
        // detail view of a single book
        System.out.println(id);
        System.out.println(id.getClass());
        Optional<Book> book = bookService.findById(id);
        System.out.println(book.toString());
        model.addAttribute("book", book.get());
        model.addAttribute("backToSearchURL", LastSearch.INSTANCE.getLastSearchURL());
        return "book_details";
    }
}
