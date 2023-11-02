package com.testsoftware.stocktrackr;

import static org.junit.jupiter.api.Assertions.*;

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
    public void test_addProductToStock() throws Exception {
        stock.addProduct(Product.createProduct("Product", "", 10, 100, 0));

        assertEquals(1, stock.getProducts().size());
    }

    @Test
    public void test_addTwoMoreProductsToStock() throws Exception {
        stock.addProduct(Product.createProduct("Product", "", 10, 100, 0));
        stock.addProduct(Product.createProduct("Product1", "", 10, 100, 0));
        stock.addProduct(Product.createProduct("Product2", "", 10, 100, 0));

        assertEquals(3, stock.getProducts().size());
    }

    @Test
    public void test_getProductFromStock() throws Exception {
        stock.addProduct(Product.createProduct("Product", "", 10, 100, 0));

        Product product = stock.getProductByName("Product");

        assertEquals("Product", product.getName());
    }

    @Test
    public void test_getProductNotFound() throws Exception {
        stock.addProduct(Product.createProduct("Product", "", 10, 100, 0));

        Product product = stock.getProductByName("Test");

        assertNull(product);
    }

    @Test
    public void test_getStockTotalPrice() throws Exception {
        stock.addProduct(Product.createProduct("Product", "", 5, 20, 0));
        stock.addProduct(Product.createProduct("Product1", "", 10, 100, 0));
        stock.addProduct(Product.createProduct("Product2", "", 20, 70, 0));

        double totalPrice = stock.getTotalPrice();

        assertEquals(2500, totalPrice);
    }

    @Test
    public void test_getStockTotalQuantity() throws Exception {
        stock.addProduct(Product.createProduct("Product", "", 5, 20, 0));
        stock.addProduct(Product.createProduct("Product1", "", 10, 100, 0));
        stock.addProduct(Product.createProduct("Product2", "", 20, 70, 0));

        int totalQuantity = stock.getTotalQuantity();

        assertEquals(190, totalQuantity);
    }

    @Test
    public void test_insertInvalidProductPrice() throws Exception {
        stock.addProduct(Product.createProduct("Product", "", -10, 20, 0));

        assertTrue(stock.getProducts().isEmpty());
    }

    @Test
    public void test_insertInvalidProductQuantity() throws Exception {
        stock.addProduct(Product.createProduct("Product", "", 10, -5, 0));

        assertTrue(stock.getProducts().isEmpty());
    }

    @Test
    public void test_updateQuantityProductFromStock() throws Exception {
        stock.addProduct(Product.createProduct("Product", "", 10, 20, 0));

        boolean updated = stock.updateProductQuantity("Product", 10);

        Product product = stock.getProductByName("Product");

        assertTrue(updated);
        assertEquals(10, product.getQuantity());
    }

    @Test
    public void test_updateNegativeQuantityProductFromStock() throws Exception {
        stock.addProduct(Product.createProduct("Product", "", 10, 20, 0));

        boolean updated = stock.updateProductQuantity("Product", -10);

        Product product = stock.getProductByName("Product");

        assertFalse(updated);
        assertEquals(20, product.getQuantity());
    }

    @Test
    public void test_removeProductFromStock() throws Exception {
        stock.addProduct(Product.createProduct("Product", "", 10, 20, 0));

        boolean updated = stock.removeProductQuantityFromStock("Product");

        Product product = stock.getProductByName("Product");

        assertTrue(updated);
        assertEquals(0, product.getQuantity());
    }

    @Test
    public void test_removeInexistentProductFromStock() throws Exception {
        stock.addProduct(Product.createProduct("Product", "", 10, 20, 0));

        boolean updated = stock.removeProductQuantityFromStock("ProductTest");

        Product product = stock.getProductByName("Product");

        assertTrue(updated);
        assertEquals(20, product.getQuantity());
    }

    @Test
    public void test_checkProductQuantityInStock() throws Exception {
        stock.addProduct(Product.createProduct("Product", "", 10, 20, 0));

        boolean result = stock.checkIfProductQuantityIsEnough("Product", 10);

        assertTrue(result);
    }

    @Test
    public void test_checkProductQuantityNotInStock() throws Exception {
        stock.addProduct(Product.createProduct("Product", "", 10, 20, 0));

        boolean result = stock.checkIfProductQuantityIsEnough("Product", 30);

        assertFalse(result);
    }

    @Test
    public void test_checkProductQuantityInStockIsEqual() throws Exception {
        stock.addProduct(Product.createProduct("Product", "", 10, 20, 0));

        boolean result = stock.checkIfProductQuantityIsEnough("Product", 20);

        assertTrue(result);
    }

    @Test
    public void test_insertProductThatAlreadyExists() throws Exception {
        stock.addProduct(Product.createProduct("Product", "", 10, 20, 0));

        assertThrows(Exception.class, () -> {
            stock.addProduct(Product.createProduct("Product", "", 10, 20, 0));
        });

        assertEquals(1, stock.getProducts().size());
    }
}
