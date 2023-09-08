package com.example.libadmin.controller;

import com.example.libadmin.domain.Book;
import com.example.libadmin.domain.Search;
import com.example.libadmin.service.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.List;

@Controller
public class SearchController {
    private BookService bookService;

    public SearchController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/all")
    public String list(Model model) {
        model.addAttribute("books",bookService.findAll());
        // return "list";
        return "redirect:/result";
    }

    @GetMapping("/result")
    public String getPaginatedCountries(@RequestParam(required = false) String term,
                                        @RequestParam(required = false) String searchBarFilter,
                                        @RequestParam(required = false) String sideBarFilter,
                                        @RequestParam(required = false) List<String> departments,
                                        @RequestParam(required = false) List<String> accessTypes,
                                        @RequestParam(required = false) List<String> languages,
                                        @RequestParam(defaultValue = "0") int pageNo,
                                        @RequestParam(defaultValue = "10") int pageSize,
                                        Model model,
                                        @ModelAttribute Search search,
                                        BindingResult errors) throws NoSuchFieldException {
        //model.addAttribute("books",bookService.findPaginated(pageNo, pageSize));
        String title = null;
        String author = null;
        Page<Book> books;
        List<Book> booksTotal;
        search.setDepartments(accessTypes);
        search.setDepartments(departments);
        search.setDepartments(languages);
        System.out.println("collected data: " + search);
        if(searchBarFilter == null) {
            // no additional filter in sidebar is activated
            /*if(sideBarFilter == null) {
                System.out.println("saw a search term" + term);
                books = bookService.searchByAuthor(term, PageRequest.of(pageNo, pageSize));
                booksTotal = bookService.searchByAuthor(term);
            } else {
                System.out.println("saw a search term and search for title" + term);
                books = bookService.searchByAuthor(term, PageRequest.of(pageNo, pageSize));
                booksTotal = bookService.searchByAuthor(term);
            }*/
            books = bookService.findAll(PageRequest.of(pageNo, pageSize));

        } else {
            System.out.println("found no search term" + term);
            //books = bookService.findAllByDepartmentIn(departments, PageRequest.of(pageNo, pageSize));
            if(searchBarFilter.equals("title")) {
                title = term;
            } else {
                author = term;
            }
            books = bookService.findAll(title, author, accessTypes, departments, languages, PageRequest.of(pageNo, pageSize));
        }
        booksTotal = bookService.findAll();


        model.addAttribute("search", search);
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



}

