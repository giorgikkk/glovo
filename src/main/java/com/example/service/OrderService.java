package com.example.service;

import com.example.entities.Order;
import com.example.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
@Slf4j
public class OrderService {
    private final OrderRepository repository;

    @Autowired
    public OrderService(OrderRepository repository) {
        this.repository = repository;
    }

    public Collection<Order> getOrders(final int page, final int pageSize) {
        log.info("getting orders on relevant page");
        return repository.getOrders(page, pageSize);
    }

    public Optional<Order> getOrder(final long id) {
        log.info("getting order by id");
        return repository.getOrder(id);
    }

    public void addOrder(final Order order) {
        log.info("creating new order");
        repository.addOrder(order);
    }

    public Order updateOrder(final long id, final Order order) {
        log.info("updating order");
        return repository.updateOrder(id, order);
    }

    public Optional<Order> removeOrder(final long id) {
        log.info("removing order");
        return repository.removeOrder(id);
    }
}
