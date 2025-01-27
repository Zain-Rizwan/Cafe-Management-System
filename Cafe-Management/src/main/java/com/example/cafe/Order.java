package com.example.cafe;

import java.util.ArrayList;
import java.util.List;

class Order {
    private String itemName;
    private int amount;

    public Order(String itemName, int amount) {
        this.itemName = itemName;
        this.amount = amount;
    }

    public Order() {

    }

    public String getItemName() {
        return itemName;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "Order{" +
                "itemName='" + itemName + '\'' +
                ", amount=" + amount +
                '}';
    }
}

