package com.example.libadmin.controller;

import com.example.libadmin.LastSearch;
import com.example.libadmin.domain.Book;
import com.example.libadmin.domain.Search;
import com.example.libadmin.repository.UserRepository;
import com.example.libadmin.service.BookService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.security.Principal;
import java.util.List;

@Controller
public class SearchController {
    private BookService bookService;
    private UserRepository userRepository;

    public SearchController(BookService bookService, UserRepository userRepository) {
        this.bookService = bookService;
        this.userRepository = userRepository;
    }


    @GetMapping("/result")
    public String getSearchResultList(@RequestParam(required = false) String term,
                                        @RequestParam(required = false) String searchBarFilter,
                                        @RequestParam(required = false) String sideBarFilter,
                                        @RequestParam(required = false) List<String> departments,
                                        @RequestParam(required = false) List<String> accessTypes,
                                        @RequestParam(required = false) List<String> languages,
                                        @RequestParam(defaultValue = "0") int pageNo,
                                        @RequestParam(defaultValue = "10") int pageSize,
                                        @RequestParam(defaultValue = "date-desc") String sortCriterion,
                                        Model model,
                                        Principal principal,
                                        @ModelAttribute Search search,
                                        HttpServletRequest req,
                                        BindingResult errors) throws NoSuchFieldException {
        // set filter according to dropdown
        String title = searchBarFilter.equals("title") ? term : null;
        String author = searchBarFilter.equals("author") ? term : null;
        Page<Book> books;
        List<Book> booksTotal;


        languages = languages != null && languages.isEmpty() ? null : languages;
        departments = departments != null && departments.isEmpty() ? null : departments;
        accessTypes = accessTypes != null && accessTypes.isEmpty() ? null : accessTypes;

        System.out.println("collected data: " + search);
        System.out.println("im in searchcontroller and this is the term: " + term);
        System.out.println("current url:   " + req.getRequestURL().toString() + "?" + req.getQueryString());
        LastSearch lastSearch = LastSearch.INSTANCE;
        lastSearch.setLastSearchURL(req.getRequestURL().toString() + "?" + req.getQueryString());
        System.out.println(lastSearch.getLastSearchURL());

        Pageable pr;
        System.out.println("here comes the sort: " + sortCriterion);
        if(sortCriterion.contains(Character.toString('-'))) {
            //PageRequest pr;
            System.out.println("got here");
            pr = PageRequest.of(pageNo, pageSize, Sort.by("publicationDate").descending());
        } else {
            pr = PageRequest.of(pageNo, pageSize, Sort.by(sortCriterion));
        }
        books = bookService.findAll(title, author, accessTypes, departments, languages, pr);
        booksTotal = bookService.findAll();

        if(principal != null) {
            model.addAttribute("bookmarked", userRepository.findByUsername(principal.getName()).orElse(null).getFavorites());
        } else {
            model.addAttribute("bookmarked", null);
        }

        model.addAttribute("search", search);
        model.addAttribute("books", books.getContent());
        model.addAttribute("currentPage", books.getNumber() + 1);
        model.addAttribute("totalItems", books.getTotalElements());
        model.addAttribute("totalPages", books.getTotalPages());
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("pageNo", pageNo);
        model.addAttribute("term", term);
        model.addAttribute("searchBarFilter", searchBarFilter);
        model.addAttribute("sortCriterion", sortCriterion);
        model.addAttribute("accessTypes", accessTypes);
        model.addAttribute("departments", departments);
        model.addAttribute("languages", languages);
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

