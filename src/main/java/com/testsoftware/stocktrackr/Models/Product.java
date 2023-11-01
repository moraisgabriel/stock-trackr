package com.testsoftware.stocktrackr.Models;

import lombok.*;

@Getter
@Setter
public class Product {

    private Long id;

    private String name;

    private String description;

    private double price;

    private int quantity;

    // initiate tax using 0.00 notation so if is 5%
    // should be initiate as 0.05%
    // in order to make it work the functions that uses it.
    private double tax;

    private Product(String name, String desc, double price, int quantity) {
        this.name = name;
        this.description = desc;
        this.price = price;
        this.quantity = quantity;
    }

    public static Product createProduct(String name, String desc, double price, int quantity) {
        if (name.equals("")) {
            System.out.println("Name cannot be empty.");
            return null;
        }

        if (price <= 0) {
            System.out.println("Price cannot be zero/negative.");
            return null;
        }

        if (quantity < 0) {
            System.out.println("Quantity cannot be negative.");
            return null;
        }

        return new Product(name, desc, price, quantity);
    }

    public static Product createProduct(String name, String desc, double price, int quantity, double tax) {
        Product product = createProduct(name, desc, price, quantity);
        product.tax = tax;
        return product;
    }

    public double calculateTax() {
        return price * (1 + tax);
    }

    public void updateQuantityStock(int newQuantity) {
        if (this.quantity <= 0) {
            System.out.println("Nothing on stock!");
            return;
        }

        this.quantity += newQuantity;
    }
}
