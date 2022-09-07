package com.epam.kafka.domain;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Order {
    private Integer id;
    private String customerName;
    private String pizzaName;
    private OrderStatus orderStatus;

    public Order() {
    }

    public Order(Integer id, String customerName, String pizzaName, OrderStatus orderStatus) {
        this.id = id;
        this.orderStatus = orderStatus;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getPizzaName() {
        return pizzaName;
    }

    public void setPizzaName(String pizzaName) {
        this.pizzaName = pizzaName;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
