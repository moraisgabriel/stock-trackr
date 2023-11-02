package com.testsoftware.stocktrackr.Models;

import lombok.*;

@Getter
@Setter
public class Product {

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

    public static Product createProduct(String name, String desc, double price, int quantity) throws Exception {
        if (name.equals(""))
            throw new Exception("Name cannot be empty.");

        if (price <= 0)
            throw new Exception("Price cannot be zero/negative.");

        if (quantity < 0)
            throw new Exception("Quantity cannot be negative.");

        return new Product(name, desc, price, quantity);
    }

    public static Product createProduct(String name, String desc, double price, int quantity, double tax) {
        try {
            if (tax < 0)
                tax = 0;

            Product product = createProduct(name, desc, price, quantity);
            product.tax = tax;
            return product;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    public double calculatePriceWithTax() {
        return price * (1 + tax);
    }

    public void updateQuantityStock(int newQuantity) throws Exception {
        if (this.quantity <= 0 && newQuantity < 0)
            throw new Exception("Nothing on stock!");

        this.quantity += newQuantity;
    }
}
