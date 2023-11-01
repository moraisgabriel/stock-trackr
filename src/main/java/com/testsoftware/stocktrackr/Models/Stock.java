package com.testsoftware.stocktrackr.Models;

import java.util.ArrayList;
import java.util.List;

import lombok.*;

@Getter
@Setter
public class Stock {
    private List<Product> products;

    public Stock() {
        this.products = new ArrayList<Product>();
    }

    public void addProduct(Product product) {
        if (product == null)
            return;

        this.products.add(product);
    }

    public Product getProductByName(String name) {
        for (Product product : this.products) {
            if (product.getName().equals(name))
                return product;
        }

        return null;
    }

    public double getTotalPrice() {
        int sum = 0;
        for (Product product : products) {
            sum += product.getQuantity() * product.getPrice();
        }

        return sum;
    }

    public int getTotalQuantity() {
        int sum = 0;
        for (Product product : products) {
            sum += product.getQuantity();
        }

        return sum;
    }

    public boolean updateProductQuantity(String productName, int newQuantity) {
        if (newQuantity < 0)
            return false;

        for (Product product : this.products) {
            if (product.getName().equals(productName))
                product.setQuantity(newQuantity);
        }

        return true;
    }
}
