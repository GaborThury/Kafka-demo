package com.epam.kafka.domain;

public enum OrderStatus {
    WAITING_FOR_BAKING,
    BEING_BAKED,
    READY_FOR_DELIVERY,
    IN_DELIVERY,
    COMPLETED
}
