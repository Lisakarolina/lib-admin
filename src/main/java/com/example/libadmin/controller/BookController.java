package com.example.libadmin.controller;

import com.example.libadmin.domain.Book;
import com.example.libadmin.domain.Search;
import com.example.libadmin.service.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Optional;

@Controller
public class BookController {
    private BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/")
    public String list(Model model) {
        model.addAttribute("books",bookService.findAll());
        return "list";
        //return "redirect:/list";
    }

    @GetMapping("/list")
    public String getPaginatedCountries(@RequestParam(required = false) String term,
                                        @RequestParam(required = false) String author,
                                        @RequestParam(defaultValue = "0") int pageNo,
                                        @RequestParam(defaultValue = "10") int pageSize,
                                        Model model,
                                        @ModelAttribute Book book,
                                        @ModelAttribute Search search,
                                        BindingResult errors) throws NoSuchFieldException {
        //model.addAttribute("books",bookService.findPaginated(pageNo, pageSize));
        Page<Book> books;
        List<Book> booksTotal;
        System.out.println("author: " + author);
        System.out.println("whole book object: " + book);

        model.addAttribute("book", book);


        if(term != null) {
            if(author != null) {
                System.out.println("saw a search term" + term);
                books = bookService.searchByAuthor(term, PageRequest.of(pageNo, pageSize));
                booksTotal = bookService.searchByAuthor(term);
            } else {
                System.out.println("saw a search term and search for title" + term);
                books = bookService.searchByAuthor(term, PageRequest.of(pageNo, pageSize));
                booksTotal = bookService.searchByAuthor(term);
            }

        } else {
            System.out.println("found no search term" + term);
            books = bookService.findAll(PageRequest.of(pageNo, pageSize, Sort.by("publicationDate").descending()));
            booksTotal = bookService.findAll();
        }


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

    @GetMapping("/search")
    public String executeSearch(@ModelAttribute Book book, BindingResult errors, Model model) {
        // logic to process input data
        System.out.println(book);
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
        return "book_details";
        // @ModelAttribute Optional<Book> book,
    }
}
