package com.testsoftware.stocktrackr;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.testsoftware.stocktrackr.Models.*;

@SpringBootTest
public class SaleTest {

    private Sale sale;
    private Stock stock;

    private SaleTest() throws Exception {
        stock = new Stock();
        stock.addProduct(Product.createProduct("Product", "", 10, 100, 0));
        
        sale = new Sale(stock);
    }

    @Test
    public void test_createEmptySale() {
        assertTrue(sale.getProducts().isEmpty());
    }

    @Test
    public void test_addProductToSale() throws Exception {
        sale.addSaleProduct("Product", 1);

        assertEquals(1, sale.getProducts().size());
    }

    @Test
    public void test_addEmptyNameProductToSale() {
        assertThrows(Exception.class, () -> {
            sale.addSaleProduct("", 1);
        });

        assertEquals(0, sale.getProducts().size());
    }

    @Test
    public void test_addInvalidProductToSale() {
        assertThrows(Exception.class, () -> {
            sale.addSaleProduct("Test", 1);
        });

        assertEquals(0, sale.getProducts().size());
    }

    @Test
    public void test_addProductWithNotEnoughQuantity() {
        assertThrows(Exception.class, () -> {
            sale.addSaleProduct("Product", 110);
        });

        assertEquals(0, sale.getProducts().size());
    }

    @Test
    public void test_addInvalidAndValidProductsToSale() throws Exception {
        sale.addSaleProduct("Product", 2);
        
        assertThrows(Exception.class, () -> {
            sale.addSaleProduct("", 1);
        });
        
        assertEquals(1, sale.getProducts().size());
        assertEquals(20, sale.getTotalAmount());
    }

    @Test
    public void test_addTwoMoreProductsToSale() throws Exception {
        stock.addProduct(Product.createProduct("Product1", "", 10, 50, 0.01));
        stock.addProduct(Product.createProduct("Product2", "", 20, 1, 0.01));

        sale.addSaleProduct("Product", 1);
        sale.addSaleProduct("Product1", 1);
        sale.addSaleProduct("Product2", 1);

        assertEquals(3, sale.getProducts().size());
    }

    @Test
    public void test_calculatePriceWithOneProduct() throws Exception {
        sale.addSaleProduct("Product", 1);

        assertEquals(10, sale.getTotalAmount());
    }

    @Test
    public void test_calculatePriceWithOneProductMultipleTime() throws Exception {
        sale.addSaleProduct("Product", 5);

        assertEquals(50, sale.getTotalAmount());
    }

    @Test
    public void test_calculatePriceWithTwoMoreProducts() throws Exception {        
        stock.addProduct(Product.createProduct("Product1", "", 10, 100, 0.01));

        sale.addSaleProduct("Product", 1);
        sale.addSaleProduct("Product1", 1);

        assertEquals(20.1, sale.getTotalAmount());
    }

    @Test
    public void test_calculateChangeWithCash() throws Exception {
        stock.addProduct(Product.createProduct("Product1", "", 20, 10, 0.1));

        sale.addSaleProduct("Product", 1);
        sale.addSaleProduct("Product1", 1);

        double change = sale.calculateChange(40, PaymentType.CASH);

        assertEquals(8, change);
    }

    @Test
    public void test_calculateChangeWithCashNegative() throws Exception {        
        sale.addSaleProduct("Product", 1);

        assertThrows(Exception.class, () -> {
            sale.calculateChange(-70, PaymentType.CASH);
        });
    }

    @Test
    public void test_calculateChangeWithCreditCard() throws Exception {
        sale.addSaleProduct("Product", 7);

        double change = sale.calculateChange(70, PaymentType.CREDIT_CARD);

        assertEquals(0, change);
    }

    @Test
    public void test_calculateChangeWithPix() throws Exception {
        sale.addSaleProduct("Product", 7);

        double change = sale.calculateChange(70, PaymentType.PIX);

        assertEquals(0, change);
    }
    
    @Test
    public void test_checkoutPaymentWithCash() throws Exception {        
        sale.addSaleProduct("Product", 1);

        boolean result = sale.checkoutPayment(50, PaymentType.CASH);

        assertTrue(result);
        assertTrue(sale.isSold());
        assertNotNull(sale.getDate());
        assertEquals(40, sale.getChange());
    }
    
    @Test
    public void test_checkoutPaymentWithPix() throws Exception {        
        sale.addSaleProduct("Product", 1);

        boolean result = sale.checkoutPayment(10, PaymentType.PIX);

        assertTrue(result);
        assertTrue(sale.isSold());
        assertNotNull(sale.getDate());
        assertEquals(0, sale.getChange());
    }
    
    @Test
    public void test_checkoutPaymentWithCredit() throws Exception {        
        sale.addSaleProduct("Product", 1);

        boolean result = sale.checkoutPayment(10, PaymentType.CASH);

        assertTrue(result);
        assertTrue(sale.isSold());
        assertNotNull(sale.getDate());
        assertEquals(0, sale.getChange());
    }
    
    @Test
    public void test_checkoutWithPaymentAboveTotal() throws Exception {        
        sale.addSaleProduct("Product", 1);

        assertThrows(Exception.class, () -> {
            sale.checkoutPayment(70, PaymentType.PIX);
        });

        assertFalse(sale.isSold());
        assertNull(sale.getDate());
        assertEquals(0, sale.getChange());
    }

    @Test
    public void test_checkoutWithPaymentNotEnough() throws Exception {        
        sale.addSaleProduct("Product", 1);

        assertThrows(Exception.class, () -> {
            sale.checkoutPayment(5, PaymentType.CASH);
        });
    }    

    @Test
    public void test_checkoutWithSuccess() throws Exception {        
        sale.addSaleProduct("Product", 1);

        boolean result = sale.checkoutPayment(10, PaymentType.CASH);

        assertTrue(result);
        assertTrue(sale.isSold());
        assertNotNull(sale.getDate());
        assertEquals(0, sale.getChange());
        assertEquals(99, stock.getProductByName("Product").getQuantity());
    }
}
