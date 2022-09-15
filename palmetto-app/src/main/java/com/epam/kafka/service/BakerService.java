package com.epam.kafka.service;

import com.epam.kafka.domain.Order;
import com.epam.kafka.domain.OrderStatus;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class BakerService {

    private final NotificationProducer notificationProducer;
    private static final Logger LOGGER = java.util.logging.Logger.getLogger(BakerService.class.getName());


    public BakerService(NotificationProducer notificationProducer) {
        this.notificationProducer = notificationProducer;
    }

    public void bake(Order order) {
        LOGGER.info("Baking...");
        order.setOrderStatus(OrderStatus.BEING_BAKED);
        notificationProducer.send(order);

        LOGGER.info("Baking is complete, pizza is ready for delivery!");
        order.setOrderStatus(OrderStatus.READY_FOR_DELIVERY);
        notificationProducer.send(order);
    }
}
