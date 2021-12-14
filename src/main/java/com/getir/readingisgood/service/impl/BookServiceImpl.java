package com.getir.readingisgood.service.impl;

import com.getir.readingisgood.dto.RegisterBookRequest;
import com.getir.readingisgood.dto.UpdateBookStockRequest;
import com.getir.readingisgood.model.Book;
import com.getir.readingisgood.repository.BookRepository;
import com.getir.readingisgood.service.BookService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    Logger logger = LoggerFactory.getLogger(BookServiceImpl.class);

    private final BookRepository bookRepository;

    @Override
    public Book registerBook(RegisterBookRequest request) {
        try {
            Book book = new Book();
            book.setName(request.getName());
            book.setAuthor(request.getAuthor());
            if (request.getStock() <= 0) {
                book.setStock(0);
            } else {
                book.setStock(request.getStock());
            }
            return bookRepository.save(book);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    @Override
    public Book updateBookStock(UpdateBookStockRequest request) {
        int newStock = request.getStock();
        if (newStock <= 0) {
            newStock = 0;
        }

        Optional<Book> book = bookRepository.findById(request.getId());
        if (book.isPresent()) {
            book.get().setStock(newStock);
            return bookRepository.save(book.get());
        }
        return null;
    }
}
