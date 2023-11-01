package com.testsoftware.stocktrackr.Models;

import java.util.*;

import lombok.*;

@Getter
@Setter
public class Stock {
    private List<Product> products = new ArrayList<Product>();
    private double totalPrice = 0.0;
    private int totalQuantity = 0;

    public void addProduct(Product product) throws Exception {
        if (product == null)
            return;
        
        if(checkIfProductAlreadyExists(product.getName()))
            throw new Exception("Product already exists in stock!");

        this.products.add(product);
        this.totalPrice += product.getQuantity() * product.getPrice();
        this.totalQuantity += product.getQuantity();
    }

    public boolean checkIfProductAlreadyExists(String productName) {
        for (Product product : products) {
            if(productName.equals(product.getName()))
                return true;
        }

        return false;
    }

    public boolean removeProduct(String productName) {
        return updateProductQuantity(productName, 0);
    }

    public Product getProductByName(String name) {
        for (Product product : this.products) {
            if (product.getName().equals(name))
                return product;
        }

        return null;
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

    public boolean checkIfProductQuantityIsEnough(String productName, int quantity) {
        Product product = getProductByName(productName);

        return quantity <= product.getQuantity();
    }
}
