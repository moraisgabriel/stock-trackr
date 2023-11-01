package com.testsoftware.stocktrackr;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import com.testsoftware.stocktrackr.Models.Product;
import com.testsoftware.stocktrackr.Models.Stock;

public class StockTests {

    @Test
    public void test_createEmptyStock() {
        Stock stock = new Stock();
        assertTrue(stock.getProducts().isEmpty());
    }

    @Test
    public void test_addProductToStock() {
        Stock stock = new Stock();
        stock.addProduct(Product.createProduct("Product", "", 10, 100));

        assertEquals(1, stock.getProducts().size());
    }

    @Test
    public void test_addTwoMoreProductsToStock() {
        Stock stock = new Stock();
        stock.addProduct(Product.createProduct("Product", "", 10, 100));
        stock.addProduct(Product.createProduct("Product1", "", 10, 100));
        stock.addProduct(Product.createProduct("Product2", "", 10, 100));

        assertEquals(3, stock.getProducts().size());
    }

    @Test
    public void test_getProductFromStock() {
        Stock stock = new Stock();
        stock.addProduct(Product.createProduct("Product", "", 10, 100));

        Product product = stock.getProductByName("Product");

        assertEquals("Product", product.getName());
    }

    @Test
    public void test_getProductNotFound() {
        Stock stock = new Stock();
        stock.addProduct(Product.createProduct("Product", "", 10, 100));

        Product product = stock.getProductByName("Test");

        assertNull(product);
    }

    @Test
    public void test_getStockTotalPrice() {
        Stock stock = new Stock();
        stock.addProduct(Product.createProduct("Product", "", 5, 20));
        stock.addProduct(Product.createProduct("Product1", "", 10, 100));
        stock.addProduct(Product.createProduct("Product2", "", 20, 70));

        double totalPrice = stock.getTotalPrice();

        assertEquals(2500, totalPrice);
    }

    @Test
    public void test_getStockTotalQuantity() {
        Stock stock = new Stock();
        stock.addProduct(Product.createProduct("Product", "", 5, 20));
        stock.addProduct(Product.createProduct("Product1", "", 10, 100));
        stock.addProduct(Product.createProduct("Product2", "", 20, 70));

        int totalQuantity = stock.getTotalQuantity();

        assertEquals(190, totalQuantity);
    }

    @Test
    public void test_insertInvalidProductPrice() {
        Stock stock = new Stock();
        stock.addProduct(Product.createProduct("Product", "", -10, 20));

        assertTrue(stock.getProducts().isEmpty());
    }

    @Test
    public void test_insertInvalidProductQuantity() {
        Stock stock = new Stock();
        stock.addProduct(Product.createProduct("Product", "", 10, -5));

        assertTrue(stock.getProducts().isEmpty());
    }

    @Test
    public void test_updateQuantityProductFromStock() {
        Stock stock = new Stock();
        stock.addProduct(Product.createProduct("Product", "", 10, 20));

        boolean updated = stock.updateProductQuantity("Product", 10);

        Product product = stock.getProductByName("Product");

        assertTrue(updated);
        assertEquals(10, product.getQuantity());
    }

    @Test
    public void test_updateNegativeQuantityProductFromStock() {
        Stock stock = new Stock();
        stock.addProduct(Product.createProduct("Product", "", 10, 20));

        boolean updated = stock.updateProductQuantity("Product", -10);

        Product product = stock.getProductByName("Product");

        assertFalse(updated);
        assertEquals(20, product.getQuantity());
    }
}
