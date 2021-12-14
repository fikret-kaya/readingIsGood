package com.getir.readingisgood.controller;

import com.getir.readingisgood.dto.*;
import com.getir.readingisgood.model.Book;
import com.getir.readingisgood.model.Customer;
import com.getir.readingisgood.model.Order;
import com.getir.readingisgood.security.model.AuthenticationRequest;
import com.getir.readingisgood.security.model.AuthenticationResponse;
import com.getir.readingisgood.security.service.CustomUserDetailsService;
import com.getir.readingisgood.security.util.JwtTokenUtil;
import com.getir.readingisgood.service.BookService;
import com.getir.readingisgood.service.CustomerService;
import com.getir.readingisgood.service.OrderService;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BooksRetailController {

    Logger logger = LoggerFactory.getLogger(BooksRetailController.class);

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private CustomUserDetailsService userDetailsService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private BookService bookService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private OrderService orderService;

    @RequestMapping({"/hello"})
    public String hello() {
        return "Hello World!";
    }

    @PostMapping("/registercustomer")
    public ResponseEntity<?> registerCustomer(@RequestBody RegisterCustomerRequest request) {
        Customer customer = customerService.registerCustomer(request);

        if (customer == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new Gson().toJson("Customer not registered to the database"));
        }
        return ResponseEntity.ok(customer);
    }

    @GetMapping("/querycustomerorder")
    public ResponseEntity<?> queryCustomerOrder(@RequestBody QueryOrderRequest request) {
        List<Order> orders = customerService.queryAllOrdersOfCustomer(request);

        if (orders == null || orders.size() == 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new Gson().toJson("No order found for given customer"));
        }
        return ResponseEntity.ok(orders);
    }

    @PostMapping("/registerbook")
    public ResponseEntity<?> registerBook(@RequestBody RegisterBookRequest request) {
        Book book = bookService.registerBook(request);

        if (book == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new Gson().toJson("Book not registered to the database"));
        }
        return ResponseEntity.ok(book);
    }

    @PostMapping("/updatestock")
    public ResponseEntity<?> updateStock(@RequestBody UpdateBookStockRequest request) {
        Book book = bookService.updateBookStock(request);

        if (book == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new Gson().toJson("Book not found"));
        }
        return ResponseEntity.ok(book);
    }

    @PostMapping("/registerorder")
    public ResponseEntity<?> registerOrder(@RequestBody RegisterOrderRequest request) {
        try {
            Order order = orderService.registerOrder(request);

            if (order == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        new Gson().toJson("Order not issued"));
            }
            return ResponseEntity.ok(order);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new Gson().toJson("Order not issued"));
        }
    }

    @GetMapping("/queryorder")
    public ResponseEntity<?> queryOrder(@RequestBody QueryOrderRequest request) {
        Order order = orderService.findOrder(request);

        if (order == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new Gson().toJson("No order found for given orderId"));
        }
        return ResponseEntity.ok(order);
    }

    @GetMapping("/queryorderbydate")
    public ResponseEntity<?> queryOrderByDate(@RequestBody QueryOrderByDateRequest request) {
        List<Order> orders = orderService.findOrdersByDateInterval(request.getStartDate(), request.getEndDate());

        if (orders == null || orders.size() == 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new Gson().toJson("No order found for given time interval"));
        }
        return ResponseEntity.ok(orders);
    }


    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationMethod(@RequestBody AuthenticationRequest request) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    request.getUsername(), request.getPassword()));
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect Credentials", e);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        final String jwt = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }
}
