package com.epam.kafka.domain;

public class OrderDto {
    private String customerName;
    private String pizzaName;

    public OrderDto() {
    }

    public OrderDto(String customerName, String pizzaName) {
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
}
