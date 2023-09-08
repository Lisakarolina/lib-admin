package com.example.libadmin.repository;

import com.example.libadmin.domain.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

public interface SearchRepository {

    /*@Query(value = "SELECT book FROM Book book WHERE book.author = :author")
    Page<Book> findWithAuthorFilter(@Param("author") String author, Pageable pageable);

    @Query(value = "SELECT book FROM Book book WHERE book.title LIKE CONCAT('%',:title,'%')")
    Page<Book> findWithTitleFilter(@Param("title") String title, Pageable pageable);

    @Query(value = "SELECT book FROM Book book WHERE book.author = :author AND book.")
    Page<Book> findWithAdditionalFilter(@Param("searchBarFilter") String searchBarFilter, @Param("addFilter") String addFilter, Pageable pageable);*/

    /*public <T> Long getCountOfEntity(Class<T> claz) {

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> studentQuery = builder.createQuery(Long.class);
        Root<T> root = studentQuery.from(claz);
        Expression<Long> countExpression = builder.count(root);
        studentQuery.select(countExpression);
        TypedQuery<Long> typedStudentQuery = em.createQuery(studentQuery);

        return typedStudentQuery.getSingleResult();
    }*/


}
