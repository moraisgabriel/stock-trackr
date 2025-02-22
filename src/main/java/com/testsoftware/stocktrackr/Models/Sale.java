package com.testsoftware.stocktrackr.Models;

import java.util.*;

import lombok.*;

@Getter
@Setter
public class Sale {
    Map<Product, Integer> products = new HashMap<Product, Integer>();
    double totalAmount = 0.0;
    Date date;
    boolean sold = false;
    double change = 0.0;
    private Stock stock = null;

    public Sale(Stock stock) {
        this.stock = stock;
    }

    public void addSaleProduct(String productName, int quantity) throws Exception {
        if(productName.isEmpty())
            throw new Exception("Product not valid!");
            
        Product product = stock.getProductByName(productName);
        if(product == null)
            throw new Exception("Product not exist!");

        boolean isQuantityEnough = stock.checkIfProductQuantityIsEnough(productName, quantity);
        if(!isQuantityEnough)
            throw new Exception("Quantity of product in stock is not enough!");

        this.products.put(product, quantity);
        this.totalAmount += product.calculatePriceWithTax() * quantity;
    }

    public double calculateChange(double paymentValue, PaymentType paymentType) throws Exception {
        if(isCreditOrPix(paymentType))
            return 0;
        
        if(paymentValue <= 0)
            throw new Exception("Payment value cannot be zero/negative!");
        
        return paymentValue - this.totalAmount;
    }

    private boolean isCreditOrPix(PaymentType paymentType) {
        return paymentType == PaymentType.CREDIT_CARD || paymentType == PaymentType.PIX;
    }

    public boolean checkoutPayment(double paymentValue, PaymentType paymentType) throws Exception {
        if(paymentValue < this.totalAmount)
            throw new Exception("Payment denied!");

        if(paymentValue > this.totalAmount && isCreditOrPix(paymentType))
            throw new Exception("Payment should be the same value of total!");
        
        this.change = calculateChange(paymentValue, paymentType);
        this.date = new Date();

        this.products.forEach((product, quantity) -> {
            int newQuantity = product.getQuantity() - quantity;
            stock.updateProductQuantity(product.getName(), newQuantity);
        });

        return this.sold = true;
    }
}
