package com.testsoftware.stocktrackr;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.testsoftware.stocktrackr.Models.Product;

@SpringBootTest
class ProductTests {

	@Test
	void test_createProductAndVerifyPrice() {
		Product product = Product.createProduct("Test product", "Description", 19.90, 100);

		assertEquals("Test product", product.getName());
		assertEquals(19.90, product.getPrice());
	}

	@Test
	void test_createProductWithPriceNegative() {
		Product product = Product.createProduct("Test product", "Description", -10, 100);

		assertNull(product);
	}

	@Test
	void test_createProductWithPriceZero() {
		Product product = Product.createProduct("Test product", "Description", 0, 100);

		assertNull(product);
	}

	@Test
	void test_createProductWithEmptyName() {
		Product product = Product.createProduct("Test product", "Description", 0, 100);

		assertNull(product);
	}

	@Test
	void test_createProductWithNegativeQuantity() {
		Product product = Product.createProduct("Test product", "Description", 19.90, -10);

		assertNull(product);
	}

	@Test
	void test_updateQuantityInStock() {
		Product product = Product.createProduct("Test product", "Description", 19.90, 100);

		product.updateQuantityStock(50);

		assertEquals(150, product.getQuantity());
	}

	@Test
	void test_updateQuantityWithNegativeValue() {
		Product product = Product.createProduct("Test product", "Description", 19.90, 100);

		product.updateQuantityStock(-10);

		assertEquals(90, product.getQuantity());
	}

	@Test
	void test_updateQuantityWithQuantityZeroAndValueNegative() {
		Product product = Product.createProduct("Test product", "Description", 19.90, 0);

		product.updateQuantityStock(-10);

		assertEquals(0, product.getQuantity());
	}

	@Test
	void test_createProductWithTax() {
		Product product = Product.createProduct("Test product", "Description", 19.90, 0, 0.05);

		assertNotNull(product.getTax());
	}
}
