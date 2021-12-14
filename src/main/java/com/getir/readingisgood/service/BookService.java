package com.getir.readingisgood.service;

import com.getir.readingisgood.dto.RegisterBookRequest;
import com.getir.readingisgood.dto.UpdateBookStockRequest;
import com.getir.readingisgood.model.Book;

public interface BookService {
    Book registerBook(RegisterBookRequest request);
    Book updateBookStock(UpdateBookStockRequest request);

}
