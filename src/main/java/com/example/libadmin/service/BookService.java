package com.example.libadmin.service;

import com.example.libadmin.domain.Book;
import com.example.libadmin.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import com.example.libadmin.service.BookSpecifications;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

// import static com.example.libadmin.service.BookSpecifications.hasOneOfDepartments;
import static org.springframework.data.jpa.domain.Specification.where;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;
    //private final BookSpecifications bookSpecification = new BookSpecifications();

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

    public Page<Book> findAll(Pageable p) {
        return bookRepository.findAll(p);
    }

    public Optional<Book> findById(Long id) {
        return bookRepository.findById(id);
    }

    public Page<Book> searchByAuthor(String author, Pageable pageable) {
        return bookRepository.findByAuthorContaining(author, pageable);
    }

    public List<Book> searchByAuthor(String author) {
        return bookRepository.findByAuthorContaining(author);
    }

    public Page<Book> findAllByDepartmentIn(List<String> departments, Pageable pageable) {
        Specification<Book> spec = BookSpecifications.hasParticularDepartments(departments);
        if(departments == null || departments.isEmpty()) {
            return bookRepository.findAll(pageable);
        }
        return bookRepository.findAll(
                where(spec), pageable);
    }

    public Page<Book> findAll(String title, String author, List<String> accessTypes, List<String> departments, List<String> languages, Pageable p) {
        Specification<Book> spec = Specification
                .where(accessTypes == null ? null : BookSpecifications.hasParticularAccessTypes(accessTypes))
                .and(departments == null ? null : BookSpecifications.hasParticularDepartments(departments))
                .and(languages == null ? null : BookSpecifications.hasParticularLanguages(languages))
                .and(author == null ? null : BookSpecifications.authorContains(author.toLowerCase()))
                .and(title == null ? null : BookSpecifications.titleContains(title.toLowerCase()));
        // if(p == null) return bookRepository.findAll(spec);
        return bookRepository.findAll(spec, p);
    }

    /*public Page<Book> findPaginated(int pageNo, int pageSize) {

        Pageable paging = PageRequest.of(pageNo, pageSize);
        Page<Book> pagedResult = bookRepository.findAll(paging);

        //return pagedResult.toList();
        return pagedResult;
    }*/
}
