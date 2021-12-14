package com.getir.readingisgood.service.impl;

import com.getir.readingisgood.dto.QueryOrderRequest;
import com.getir.readingisgood.dto.RegisterOrderRequest;
import com.getir.readingisgood.model.Book;
import com.getir.readingisgood.model.Customer;
import com.getir.readingisgood.model.Order;
import com.getir.readingisgood.repository.BookRepository;
import com.getir.readingisgood.repository.CustomerRepository;
import com.getir.readingisgood.repository.OrderRepository;
import com.getir.readingisgood.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final BookRepository bookRepository;

    @Override
    @Transactional
    public Order registerOrder(RegisterOrderRequest request) {
        try {
            Order order = new Order();
            order.setCustomer(customerRepository.findById(request.getCustomerId()).get());

            List<Book> books = new ArrayList<>();
            for(Long id : request.getBooks().keySet()) {
                Optional<Book> book = bookRepository.findById(id);
                if (book.isPresent() && book.get().getStock() >= request.getBooks().get(id)) {
                    book.get().setStock(book.get().getStock() - request.getBooks().get(id));

                    if (bookRepository.save(book.get()) != null) {
                        books.add(book.get());
                    }
                } else {
                    throw new Exception("At least one of the book's stock not enough anymore!");
                }
            }
            order.setBooks(books);
            order.setDate(new Date());

            return orderRepository.save(order);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Order findOrder(QueryOrderRequest request) {
        return orderRepository.findById(request.getId()).get();
    }

    @Override
    public List<Order> findOrdersByDateInterval(Date startDate, Date endDate) {
        return orderRepository.findByDateBetween(startDate, endDate);
    }
}
