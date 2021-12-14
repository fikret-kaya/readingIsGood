package com.getir.readingisgood.service.impl;

import com.getir.readingisgood.dto.QueryOrderRequest;
import com.getir.readingisgood.dto.RegisterCustomerRequest;
import com.getir.readingisgood.model.Customer;
import com.getir.readingisgood.model.Order;
import com.getir.readingisgood.repository.CustomerRepository;
import com.getir.readingisgood.repository.OrderRepository;
import com.getir.readingisgood.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;

    @Override
    public Customer registerCustomer(RegisterCustomerRequest request) {
        try {
            Customer customer = new Customer();
            customer.setEmail(request.getEmail().toLowerCase());
            customer.setName(request.getName().toLowerCase());
            customer.setSurname(request.getSurname().toLowerCase());
            return customerRepository.save(customer);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    @Override
    public List<Order> queryAllOrdersOfCustomer(QueryOrderRequest request) {
        try {
            return orderRepository.findAllOrders(request.getId());
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ArrayList<>();
        }
    }
}
