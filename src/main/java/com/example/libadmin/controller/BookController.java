package com.example.libadmin.controller;

import com.example.libadmin.domain.Book;
import com.example.libadmin.service.BookService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.List;

@Controller
public class BookController {
    private BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/")
    public String list(Model model) {
        model.addAttribute("books",bookService.findAll());
        // return "list";
        return "redirect:/list/0/10";
    }

    @GetMapping("/list/{pageNo}/{pageSize}")
    public String getPaginatedCountries(@PathVariable int pageNo,
                                            @PathVariable int pageSize, Model model) throws NoSuchFieldException {
        //model.addAttribute("books",bookService.findPaginated(pageNo, pageSize));

        Page<Book> books = bookService.findPaginated(pageNo, pageSize);

        model.addAttribute("books", books.getContent());
        model.addAttribute("currentPage", books.getNumber() + 1);
        model.addAttribute("totalItems", books.getTotalElements());
        model.addAttribute("totalPages", books.getTotalPages());
        model.addAttribute("pageSize", pageSize);
        try {
            model.addAttribute("languageGroups", bookService.groupBySearchField(bookService.findAll(), "language"));
            model.addAttribute("accessGroups", bookService.groupBySearchField(bookService.findAll(), "access_type"));
            model.addAttribute("departmentGroups", bookService.groupBySearchField(bookService.findAll(), "department"));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        return "list";
    }

    @GetMapping("/search")
    public String executeSearch(@ModelAttribute Book book, BindingResult errors, Model model) {
        // logic to process input data
        System.out.println(book);
        return "list";
    }
}
