package com.example.libadmin.service;

import com.example.libadmin.domain.Book;
import com.example.libadmin.repository.BookRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;

@Service
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book save(Book book) {
        return bookRepository.save(book);
    }

    public void save(List<Book> books) {
        bookRepository.saveAll(books);
    }

    public HashMap<String,Integer> groupBySearchField(List<Book> books, String field) throws NoSuchFieldException, IllegalAccessException {
        //bookRepository.saveAll(books);
        HashMap<String, Integer> groupedResults = new HashMap<String, Integer>();

        for (Book book : books) {
            Field bookField = book.getClass().getDeclaredField(field);
            bookField.setAccessible(true);
            groupedResults.put((String) bookField.get(book), groupedResults.getOrDefault((String) bookField.get(book), 0) + 1);
            //groupedResults.put(book.getLanguage(), groupedResults.getOrDefault(field, 0) + 1);

        }
        return groupedResults;
    }

    public List<Book> findAll() {
        return (List<Book>) bookRepository.findAll();
    }

    public List<Book> searchByAuthor(String author) {
        return (List<Book>) bookRepository.findByAuthorContaining(author);
    }

    public Page<Book> findPaginated(int pageNo, int pageSize) {

        Pageable paging = PageRequest.of(pageNo, pageSize);
        Page<Book> pagedResult = bookRepository.findAll(paging);

        //return pagedResult.toList();
        return pagedResult;
    }
}
