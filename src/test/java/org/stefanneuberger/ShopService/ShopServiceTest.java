package org.stefanneuberger.ShopService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.stefanneuberger.OrderListRepo.Order;
import org.stefanneuberger.productRepo.Product;
import org.stefanneuberger.productRepo.ProductRepo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ShopServiceTest {

    private ShopService shopService;
    private Product testProduct;
    private List<Product> initialProducts;

    @BeforeEach
    void setUp() {
        testProduct = new Product(1, "Test Product", new BigDecimal("99.99"));
        initialProducts = new ArrayList<>();
        initialProducts.add(testProduct);
        initialProducts.add(new Product(2, "Another Product", new BigDecimal("49.99")));
        
        shopService = new ShopService(1, "Test Shop", initialProducts);
    }

    @Test
    void constructor_WithInitialProducts_ShouldCreateShopWithProducts() {
        // Given & When - done in setUp()
        
        // Then
        assertEquals(1, shopService.getShopId());
        assertEquals("Test Shop", shopService.getShopName());
        assertNotNull(shopService.getProductRepo());
        assertEquals(2, shopService.getProductRepo().getProducts().size());
    }

    @Test
    void createOrder_WithValidProductAndQuantity_ShouldCreateOrder() {
        // Given
        int quantity = 3;
        
        // When
        shopService.createOrder(testProduct, quantity);
        
        // Then
        List<Order> orders = shopService.getShopOrders().getOrders();
        assertEquals(1, orders.size());
        assertEquals(testProduct.id(), orders.get(0).product().id());
        assertEquals(quantity, orders.get(0).quantity());
    }

    @Test
    void createOrder_WithNullProduct_ShouldThrowException() {
        // When & Then
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> shopService.createOrder(null, 1)
        );
        assertEquals("Product cannot be null", exception.getMessage());
    }

    @Test
    void createOrder_WithZeroQuantity_ShouldThrowException() {
        // When & Then
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> shopService.createOrder(testProduct, 0)
        );
        assertEquals("Quantity must be greater than zero", exception.getMessage());
    }

    @Test
    void createOrder_WithNonExistentProduct_ShouldThrowException() {
        // Given
        Product nonExistentProduct = new Product(999, "Non-existent", new BigDecimal("1.00"));
        
        // When & Then
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> shopService.createOrder(nonExistentProduct, 1)
        );
        assertEquals("Product does not exist", exception.getMessage());
    }

    @Test
    void deleteOrder_WithValidId_ShouldRemoveOrder() {
        // Given
        shopService.createOrder(testProduct, 2);
        List<Order> orders = shopService.getShopOrders().getOrders();
        int orderId = orders.get(0).id();
        
        // When
        shopService.deleteOrder(orderId);
        
        // Then
        assertTrue(shopService.getShopOrders().getOrders().isEmpty());
    }

    @Test
    void deleteOrder_WithInvalidId_ShouldThrowException() {
        // When & Then
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> shopService.deleteOrder(0)
        );
        assertEquals("OrderId must be greater than zero", exception.getMessage());
    }

    @Test
    void updateOrder_WithValidData_ShouldUpdateOrder() {
        // Given
        shopService.createOrder(testProduct, 2);
        List<Order> orders = shopService.getShopOrders().getOrders();
        int orderId = orders.get(0).id();
        Product newProduct = new Product(2, "Another Product", new BigDecimal("49.99"));
        int newQuantity = 5;
        
        // When
        shopService.updateOrder(orderId, newQuantity, newProduct);
        
        // Then
        Order updatedOrder = shopService.getShopOrders().getOrderById(orderId);
        assertNotNull(updatedOrder);
        assertEquals(newProduct.id(), updatedOrder.product().id());
        assertEquals(newQuantity, updatedOrder.quantity());
    }

    @Test
    void updateOrder_WithInvalidData_ShouldThrowException() {
        // When & Then
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> shopService.updateOrder(0, 1, testProduct)
        );
        assertEquals("OrderId must be greater than zero", exception.getMessage());
    }

    @Test
    void setProductRepo_ShouldUpdateProductRepository() {
        // Given
        ProductRepo newRepo = new ProductRepo();
        Product newProduct = new Product(3, "New Product", new BigDecimal("29.99"));
        newRepo.addProduct(newProduct);
        
        // When
        shopService.setProductRepo(newRepo);
        
        // Then
        assertSame(newRepo, shopService.getProductRepo());
        assertEquals(1, shopService.getProductRepo().getProducts().size());
        assertEquals(newProduct, shopService.getProductRepo().getProductById(3));
    }

    @Test
    void setShopOrders_ShouldUpdateOrderList() {
        // Given
        List<Order> newOrders = new ArrayList<>();
        newOrders.add(new Order(1, testProduct, 3));
        
        // When
        shopService.setShopOrders(newOrders);
        
        // Then
        assertEquals(1, shopService.getShopOrders().getOrders().size());
        assertEquals(testProduct.id(), shopService.getShopOrders().getOrders().get(0).product().id());
    }
}