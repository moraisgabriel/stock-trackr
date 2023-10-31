package com.testsoftware.stocktrackr;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.testsoftware.stocktrackr.Models.Product;

@SpringBootTest
class ProductTests {

	@Test
	void test_createProductAndVerifyPrice() {
		Product product = new Product(1, "Test product", "Description", 19.90, 100);

		assertEquals("Test product", product.getName());
		assertEquals(19.90, product.getPrice());
	}

	@Test
	void test_updateQuantityInStock() {
		Product product = new Product(1, "Test product", "Description", 19.90, 100);

		product.updateQuantityStock(50);

		assertEquals(50, product.getQuantity());
	}
}
