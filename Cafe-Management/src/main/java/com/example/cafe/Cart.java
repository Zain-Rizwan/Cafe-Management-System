package com.example.cafe;

import java.util.ArrayList;
import java.util.List;

public class Cart extends Order {
    private final List<Order> orders;

    public Cart() {
        super();
        this.orders = new ArrayList<>();
    }

    public void addOrder(String itemName, int amount) {
        Order order = new Order(itemName, amount);
        orders.add(order);
        System.out.println("Added to cart: " + order);
    }
    public void printAllOrders() {
        System.out.println("All Orders:");
        for (Order order : orders) {
            System.out.println(order);
        }
    }
}
