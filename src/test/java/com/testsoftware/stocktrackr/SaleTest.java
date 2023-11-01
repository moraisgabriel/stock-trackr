package com.testsoftware.stocktrackr;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.testsoftware.stocktrackr.Models.*;

@SpringBootTest
public class SaleTest {

    private Sale sale;

    private SaleTest() {
        Stock stock = new Stock();

        stock.addProduct(Product.createProduct("Product", "", 10, 100, 0.01));
        stock.addProduct(Product.createProduct("Product1", "", 20, 10, 0.5));
        stock.addProduct(Product.createProduct("Product2", "", 30, 50, 0));
        
        sale = new Sale(stock);
    }

    @Test
    public void test_createEmptySale() {
        assertTrue(sale.getProducts().isEmpty());
    }

    @Test
    public void test_addProductToSale() throws Exception {
        sale.addSaleProduct(Product.createProduct("Product", "", 10, 100, 0), 1);

        assertEquals(1, sale.getProducts().size());
    }

    @Test
    public void test_addInvalidProductToSale() throws Exception {
        assertThrows(Exception.class, () -> {
            sale.addSaleProduct(Product.createProduct("Product", "", -10, 100, 0), 1);
        });

        assertEquals(0, sale.getProducts().size());
    }

    @Test
    public void test_addInvalidAndValidProductsToSale() throws Exception {
        sale.addSaleProduct(Product.createProduct("Product", "", 10, 100, 0), 2);
        
        assertThrows(Exception.class, () -> {
            sale.addSaleProduct(Product.createProduct("Product", "", -10, 100, 0), 1);
        });
        
        assertEquals(1, sale.getProducts().size());
        assertEquals(20, sale.getTotalAmount());
    }

    @Test
    public void test_addTwoMoreProductsToSale() throws Exception {
        sale.addSaleProduct(Product.createProduct("Product", "", 10, 100, 0), 1);
        sale.addSaleProduct(Product.createProduct("Product1", "", 10, 100, 0), 1);
        sale.addSaleProduct(Product.createProduct("Product2", "", 10, 100, 0), 1);

        assertEquals(3, sale.getProducts().size());
    }

    @Test
    public void test_calculatePriceWithOneProduct() throws Exception {
        sale.addSaleProduct(Product.createProduct("Product", "", 10, 100, 0.5), 1);

        assertEquals(15, sale.getTotalAmount());
    }

    @Test
    public void test_calculatePriceWithOneProductMultipleTime() throws Exception {
        sale.addSaleProduct(Product.createProduct("Product", "", 10, 100, 0.1), 5);

        assertEquals(55, sale.getTotalAmount());
    }

    @Test
    public void test_calculatePriceWithTwoMoreProducts() throws Exception {
        sale.addSaleProduct(Product.createProduct("Product", "", 10, 100, 0.5), 1);
        sale.addSaleProduct(Product.createProduct("Product1", "", 50, 100, 0.01), 1);

        assertEquals(65.5, sale.getTotalAmount());
    }

    @Test
    public void test_calculateChangeWithCash() throws Exception {
        sale.addSaleProduct(Product.createProduct("Product", "", 10, 100, 0.5), 1);
        sale.addSaleProduct(Product.createProduct("Product1", "", 50, 100, 0.01), 1);

        double change = sale.calculateChange(70, PaymentType.CASH);

        assertEquals(4.5, change);
    }

    @Test
    public void test_calculateChangeWithCashNegative() throws Exception {        
        sale.addSaleProduct(Product.createProduct("Product", "", 10, 100, 0.5), 1);
        sale.addSaleProduct(Product.createProduct("Product1", "", 50, 100, 0.01), 1);

        assertThrows(Exception.class, () -> {
            sale.calculateChange(-70, PaymentType.CASH);
        });
    }

    @Test
    public void test_calculateChangeWithCreditCard() throws Exception {
        sale.addSaleProduct(Product.createProduct("Product", "", 10, 100, 0.5), 1);
        sale.addSaleProduct(Product.createProduct("Product1", "", 50, 100, 0.01), 1);

        double change = sale.calculateChange(70, PaymentType.CREDIT_CARD);

        assertEquals(0, change);
    }

    @Test
    public void test_calculateChangeWithPix() throws Exception {
        sale.addSaleProduct(Product.createProduct("Product", "", 10, 100, 0.5), 1);
        sale.addSaleProduct(Product.createProduct("Product1", "", 50, 100, 0.01), 1);

        double change = sale.calculateChange(70, PaymentType.PIX);

        assertEquals(0, change);
    }
    
    @Test
    public void test_checkoutPaymentWithCash() throws Exception {        
        sale.addSaleProduct(Product.createProduct("Product", "", 10, 100, 0.5), 1);
        sale.addSaleProduct(Product.createProduct("Product1", "", 50, 100, 0.01), 1);

        boolean result = sale.checkoutPayment(70, PaymentType.CASH);

        assertTrue(result);
        assertTrue(sale.isSold());
        assertNotNull(sale.getDate());
        assertEquals(4.5, sale.getChange());
    }
    
    @Test
    public void test_checkoutPaymentWithPix() throws Exception {        
        sale.addSaleProduct(Product.createProduct("Product", "", 10, 100, 0.5), 1);
        sale.addSaleProduct(Product.createProduct("Product1", "", 50, 100, 0.01), 1);

        boolean result = sale.checkoutPayment(65.5, PaymentType.PIX);

        assertTrue(result);
        assertTrue(sale.isSold());
        assertNotNull(sale.getDate());
        assertEquals(0, sale.getChange());
    }
    
    @Test
    public void test_checkoutPaymentWithCredit() throws Exception {        
        sale.addSaleProduct(Product.createProduct("Product", "", 10, 100, 0.5), 1);
        sale.addSaleProduct(Product.createProduct("Product1", "", 50, 100, 0.01), 1);

        boolean result = sale.checkoutPayment(65.5, PaymentType.CASH);

        assertTrue(result);
        assertTrue(sale.isSold());
        assertNotNull(sale.getDate());
        assertEquals(0, sale.getChange());
    }
    
    @Test
    public void test_checkoutWithPaymentAboveTotal() throws Exception {        
        sale.addSaleProduct(Product.createProduct("Product", "", 10, 100, 0.5), 1);
        sale.addSaleProduct(Product.createProduct("Product1", "", 50, 100, 0.01), 1);

        assertThrows(Exception.class, () -> {
            sale.checkoutPayment(70, PaymentType.PIX);
        });

        assertFalse(sale.isSold());
        assertNull(sale.getDate());
        assertEquals(0, sale.getChange());
    }    

    @Test
    public void test_checkoutWithPaymentNotEnough() throws Exception {        
        sale.addSaleProduct(Product.createProduct("Product", "", 10, 100, 0.5), 1);
        sale.addSaleProduct(Product.createProduct("Product1", "", 50, 100, 0.01), 1);

        assertThrows(Exception.class, () -> {
            sale.checkoutPayment(50, PaymentType.CASH);
        });
    }
}
