package com.example.libadmin.repository;

import com.example.libadmin.domain.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.example.libadmin.service.BookSpecifications;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book,Long>, JpaSpecificationExecutor<Book> {
    Optional<Book> findById(Long id);

    Page<Book> findByAuthorContaining(String author, Pageable pageable);

    List<Book> findByAuthorContaining(String author);

    Page<Book> findAll(Specification<Book> spec, Pageable pageable);

    Page<Book> findAllByDepartmentIn(List<String> departments, Pageable pageable);
}
