package com.epam.kafka.domain;

public class Order {
    private String customerName;
    private String pizzaName;
    private OrderStatus orderStatus;

    public Order() {
    }

    public Order(String customerName, String pizzaName, OrderStatus orderStatus) {
        this.customerName = customerName;
        this.pizzaName = pizzaName;
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

}
