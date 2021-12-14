package com.getir.readingisgood.repository;

import com.getir.readingisgood.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query(value = "SELECT * FROM Order WHERE customer_id = ?1", nativeQuery = true)
    public List<Order> findAllOrders(Long customerId);

    List<Order> findByDateBetween(Date start, Date end);

}
