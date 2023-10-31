package com.testsoftware.stocktrackr.Models;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "products")
@Table(name = "products")
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true)
    private String name;

    private String description;

    private double price;

    private int quantity;

    private Product(int id, String name, String desc, double price, int quantity) {
        this.id = id;
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

        return new Product(1, name, desc, price, quantity);
    }

    public void updateQuantityStock(int newQuantity) {
        if (this.quantity <= 0) {
            System.out.println("Nothing on stock!");
            return;
        }

        this.quantity += newQuantity;
    }
}
