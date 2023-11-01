package com.testsoftware.stocktrackr;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.testsoftware.stocktrackr.Models.*;

@SpringBootTest
public class StockTests {

    private Stock stock = new Stock();

    @Test
    public void test_createEmptyStock() {
        assertTrue(stock.getProducts().isEmpty());
    }

    @Test
    public void test_addProductToStock() {
        stock.addProduct(Product.createProduct("Product", "", 10, 100, 0));

        assertEquals(1, stock.getProducts().size());
    }

    @Test
    public void test_addTwoMoreProductsToStock() {
        stock.addProduct(Product.createProduct("Product", "", 10, 100, 0));
        stock.addProduct(Product.createProduct("Product1", "", 10, 100, 0));
        stock.addProduct(Product.createProduct("Product2", "", 10, 100, 0));

        assertEquals(3, stock.getProducts().size());
    }

    @Test
    public void test_getProductFromStock() {
        stock.addProduct(Product.createProduct("Product", "", 10, 100, 0));

        Product product = stock.getProductByName("Product");

        assertEquals("Product", product.getName());
    }

    @Test
    public void test_getProductNotFound() {
        stock.addProduct(Product.createProduct("Product", "", 10, 100, 0));

        Product product = stock.getProductByName("Test");

        assertNull(product);
    }

    @Test
    public void test_getStockTotalPrice() {
        stock.addProduct(Product.createProduct("Product", "", 5, 20, 0));
        stock.addProduct(Product.createProduct("Product1", "", 10, 100, 0));
        stock.addProduct(Product.createProduct("Product2", "", 20, 70, 0));

        double totalPrice = stock.getTotalPrice();

        assertEquals(2500, totalPrice);
    }

    @Test
    public void test_getStockTotalQuantity() {
        stock.addProduct(Product.createProduct("Product", "", 5, 20, 0));
        stock.addProduct(Product.createProduct("Product1", "", 10, 100, 0));
        stock.addProduct(Product.createProduct("Product2", "", 20, 70, 0));

        int totalQuantity = stock.getTotalQuantity();

        assertEquals(190, totalQuantity);
    }

    @Test
    public void test_insertInvalidProductPrice() {
        stock.addProduct(Product.createProduct("Product", "", -10, 20, 0));

        assertTrue(stock.getProducts().isEmpty());
    }

    @Test
    public void test_insertInvalidProductQuantity() {
        stock.addProduct(Product.createProduct("Product", "", 10, -5, 0));

        assertTrue(stock.getProducts().isEmpty());
    }

    @Test
    public void test_updateQuantityProductFromStock() {
        stock.addProduct(Product.createProduct("Product", "", 10, 20, 0));

        boolean updated = stock.updateProductQuantity("Product", 10);

        Product product = stock.getProductByName("Product");

        assertTrue(updated);
        assertEquals(10, product.getQuantity());
    }

    @Test
    public void test_updateNegativeQuantityProductFromStock() {
        stock.addProduct(Product.createProduct("Product", "", 10, 20, 0));

        boolean updated = stock.updateProductQuantity("Product", -10);

        Product product = stock.getProductByName("Product");

        assertFalse(updated);
        assertEquals(20, product.getQuantity());
    }

    @Test
    public void test_removeProductFromStock() {
        stock.addProduct(Product.createProduct("Product", "", 10, 20, 0));

        boolean updated = stock.removeProduct("Product");

        Product product = stock.getProductByName("Product");

        assertTrue(updated);
        assertEquals(0, product.getQuantity());
    }

    @Test
    public void test_removeInexistentProductFromStock() {
        stock.addProduct(Product.createProduct("Product", "", 10, 20, 0));

        boolean updated = stock.removeProduct("ProductTest");

        Product product = stock.getProductByName("Product");

        assertTrue(updated);
        assertEquals(20, product.getQuantity());
    }

    @Test
    public void test_checkProductQuantityInStock() {
        stock.addProduct(Product.createProduct("Product", "", 10, 20, 0));

        boolean result = stock.checkIfProductQuantityIsEnough("Product", 10);

        assertTrue(result);
    }

    @Test
    public void test_checkProductQuantityNotInStock() {
        stock.addProduct(Product.createProduct("Product", "", 10, 20, 0));

        boolean result = stock.checkIfProductQuantityIsEnough("Product", 30);

        assertFalse(result);
    }

    @Test
    public void test_checkProductQuantityInStockIsEqual() {
        stock.addProduct(Product.createProduct("Product", "", 10, 20, 0));

        boolean result = stock.checkIfProductQuantityIsEnough("Product", 20);

        assertTrue(result);
    }
}
