package com.epam.kafka.service;

import com.epam.kafka.domain.Order;
import com.epam.kafka.domain.OrderDto;
import com.epam.kafka.domain.OrderStatus;
import com.epam.kafka.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderProducer orderProducer;
    private int incrementedId = 0;

    @Autowired
    public OrderService(OrderRepository orderRepository, OrderProducer orderProducer) {
        this.orderRepository = orderRepository;
        this.orderProducer = orderProducer;
    }

    public void create(OrderDto orderDto) {
        Order order = mapToOrder(orderDto);
        orderRepository.save(order);
        orderProducer.produce(order);
    }

    public Optional<Order> findById(Integer id) {
        return orderRepository.findById(id);
    }

    private Order mapToOrder(OrderDto orderDto) {
        incrementedId++; // temp hacky solution
        return new Order(incrementedId, orderDto.getCustomerName(), orderDto.getPizzaName(), OrderStatus.WAITING_FOR_BAKING);
    }
}
