package com.getir.readingisgood.repository;

import com.getir.readingisgood.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
