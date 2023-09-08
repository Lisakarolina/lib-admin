package com.example.libadmin.service;

import com.example.libadmin.domain.Book;
import org.springframework.data.jpa.domain.Specification;

import java.text.MessageFormat;
import java.util.List;

public final class BookSpecifications {

    private BookSpecifications() {};
    public static Specification<Book> titleContains(String expression) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("title")), "%" + expression + "%");
    }

    public static Specification<Book> authorContains(String expression) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("author")), contains(expression));
    }

    static Specification<Book> hasAccessType(String accessType) {
        return (book, cq, cb) -> cb.equal(book.get("access_type"), accessType);
    }

    static Specification<Book> hasDepartment(String department) {
        return (book, cq, cb) -> cb.equal(book.get("department"), department);
    }

    static Specification<Book> hasLanguage(String language) {
        return (book, cq, cb) -> cb.equal(book.get("language"), language);
    }

    static Specification<Book> hasParticularDepartments(List<String> departments) {
        return (root, query, cb) -> cb.in(root.get("department")).value(departments);
    }

    static Specification<Book> hasParticularAccessTypes(List<String> accessTypes) {
        return (root, query, cb) -> cb.in(root.get("access_type")).value(accessTypes);
    }

    static Specification<Book> hasParticularLanguages(List<String> languages) {
        return (root, query, cb) -> cb.in(root.get("language")).value(languages);
    }

    private static String contains(String expression) {
        return MessageFormat.format("%{0}%", expression.toLowerCase());
    }
}
