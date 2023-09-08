package com.example.libadmin.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name= "book")
public class Book {
    @Id
    @GeneratedValue
    private Long book_id;

    private String term;
    private String title;

    private String author;
    private String publication_place;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDate publication_date;

    private Long extent;

    private String language;

    private List<String> subject_headings;

    private String availability;

    private String access_type;

    private String department;

    public Book() {};

    public Long getBook_id() {
        return book_id;
    }

    public void setBook_id(Long book_id) {
        this.book_id = book_id;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getPublication_place() {
        return publication_place;
    }

    public void setPublication_place(String publication_place) {
        this.publication_place = publication_place;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public LocalDate getPublication_date() {
        return publication_date;
    }

    public void setPublication_date(LocalDate publication_date) {
        this.publication_date = publication_date;
    }

    public Long getExtent() {
        return extent;
    }

    public void setExtent(Long extent) {
        this.extent = extent;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public List<String> getSubject_headings() {
        return subject_headings;
    }

    public void setSubject_headings(List<String> subject_headings) {
        this.subject_headings = subject_headings;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public String getAccess_type() {
        return access_type;
    }

    public void setAccess_type(String access_type) {
        this.access_type = access_type;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(book_id, book.book_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(book_id);
    }

    @Override
    public String toString() {
        return "Book{" +
                "book_id=" + book_id + "author=" + author + "term=" + term +
                '}';
    }
}
