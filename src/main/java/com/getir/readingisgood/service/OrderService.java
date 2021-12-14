package com.getir.readingisgood.service;

import com.getir.readingisgood.dto.QueryOrderRequest;
import com.getir.readingisgood.dto.RegisterOrderRequest;
import com.getir.readingisgood.model.Order;

import java.util.Date;
import java.util.List;

public interface OrderService {
    Order registerOrder(RegisterOrderRequest request);
    Order findOrder(QueryOrderRequest request);
    List<Order> findOrdersByDateInterval(Date startDate, Date endDate);
}
