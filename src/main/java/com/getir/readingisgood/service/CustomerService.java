package com.getir.readingisgood.service;

import com.getir.readingisgood.dto.QueryOrderRequest;
import com.getir.readingisgood.dto.RegisterCustomerRequest;
import com.getir.readingisgood.model.Customer;
import com.getir.readingisgood.model.Order;

import java.util.List;

public interface CustomerService {
    Customer registerCustomer(RegisterCustomerRequest request);
    List<Order> queryAllOrdersOfCustomer(QueryOrderRequest request);
}
