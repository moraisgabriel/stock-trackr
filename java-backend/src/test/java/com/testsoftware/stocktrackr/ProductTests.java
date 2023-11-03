package com.testsoftware.stocktrackr;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.testsoftware.stocktrackr.Models.Product;

@SpringBootTest
public class ProductTests {

	@Test
	void test_createProductAndVerifyPrice() throws Exception {
		Product product = Product.createProduct("Test product", "Description", 19.90, 100);

		assertEquals("Test product", product.getName());
		assertEquals(19.90, product.getPrice());
	}

	@Test
	void test_createProductWithPriceNegative() throws Exception {
		assertThrows(Exception.class, () -> {
			Product.createProduct("Test product", "Description", -10, 100);
        });
	}

	@Test
	void test_createProductWithPriceZero() {
		assertThrows(Exception.class, () -> {
			Product.createProduct("Test product", "Description", 0, 100);
        });
	}

	@Test
	void test_createProductWithEmptyName() {
		assertThrows(Exception.class, () -> {
			Product.createProduct("", "Description", 50.99, 100);
        });
	}

	@Test
	void test_createProductWithNegativeQuantity() throws Exception {
		assertThrows(Exception.class, () -> {
			Product.createProduct("Test product", "Description", 19.90, -10);
        });
	}

	@Test
	void test_updateQuantityInStock() throws Exception {
		Product product = Product.createProduct("Test product", "Description", 19.90, 100);

		product.updateQuantityStock(50);

		assertEquals(150, product.getQuantity());
	}

	@Test
	void test_updateQuantityWithNegativeValue() throws Exception {
		Product product = Product.createProduct("Test product", "Description", 19.90, 100);

		product.updateQuantityStock(-10);

		assertEquals(90, product.getQuantity());
	}

	@Test
	void test_updateQuantityWithQuantityZero() throws Exception {
		Product product = Product.createProduct("Test product", "Description", 19.90, 0);

		product.updateQuantityStock(10);

		assertEquals(10, product.getQuantity());
	}

	@Test
	void test_updateQuantityWithQuantityZeroAndValueNegative() throws Exception {
		Product product = Product.createProduct("Test product", "Description", 19.90, 0);

		assertThrows(Exception.class, () -> {
			product.updateQuantityStock(-10);
        });

		assertEquals(0, product.getQuantity());
	}

	@Test
	void test_createProductWithTax() {
		Product product = Product.createProduct("Test product", "Description", 19.90, 0, 0.05);

		assertNotNull(product.getTax());
	}

	@Test
	void test_createProductWithTaxNegative() {
		Product product = Product.createProduct("Test product", "Description", 19.90, 0, -0.5);

		assertEquals(0, product.getTax());
	}

	@Test
	void test_getPriceWithTax() {
		Product product = Product.createProduct("Test product", "Description", 10, 0, 0.5);

		assertEquals(15, product.calculatePriceWithTax());
	}
}
