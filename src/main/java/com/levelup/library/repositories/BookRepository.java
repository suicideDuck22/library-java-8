package com.levelup.library.repositories;

import com.levelup.library.entities.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<BookEntity, Long> {
    @Query(value = "SELECT * FROM book WHERE book_status = :status", nativeQuery = true)
    List<BookEntity> findByStatus(Integer status);
}