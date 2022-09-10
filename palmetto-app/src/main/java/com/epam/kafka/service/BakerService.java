package com.epam.kafka.service;

import com.epam.kafka.domain.Order;
import com.epam.kafka.domain.OrderStatus;
import org.springframework.stereotype.Service;

@Service
public class BakerService {

    private final NotificationProducer notificationProducer;

    public BakerService(NotificationProducer notificationProducer) {
        this.notificationProducer = notificationProducer;
    }

    public void bake(Order order) {
        System.out.println("Baking...");
        order.setOrderStatus(OrderStatus.BEING_BAKED);
        notificationProducer.send(order);

        System.out.println("Baking is complete, pizza is ready for delivery!");
        order.setOrderStatus(OrderStatus.READY_FOR_DELIVERY);
        notificationProducer.send(order);
    }
}
