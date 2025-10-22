package org.stefanneuberger.OrderListRepo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.stefanneuberger.productRepo.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderListRepoTest {

    private OrderListRepo orderListRepo;
    private List<Order> initialOrders;
    private Product p1;
    private Product p2;
    private Product p3;

    @BeforeEach
    void setUp() {
        // Initialize repository
        orderListRepo = new OrderListRepo();

        // Products
        p1 = new Product(1, "keyboard ZSA voyager", new BigDecimal("123.98"));
        p2 = new Product(2, "Mini Mouse", new BigDecimal("22.98"));
        p3 = new Product(3, "Mickey Mouse", new BigDecimal("134.98"));

        // Orders
        Order o1 = new Order(101, p1, 1);
        Order o2 = new Order(102, p2, 2);
        Order o3 = new Order(103, p3, 3);

        initialOrders = new ArrayList<>();
        initialOrders.add(o1);
        initialOrders.add(o2);
        initialOrders.add(o3);
        orderListRepo.setOrders(initialOrders);
    }

    @Test
    @DisplayName("addOrder should add a new order to the list when no order with the same product present")
    void addOrder_addsNewOrderWhenProductNotPresent() {
        int initialSize = orderListRepo.getOrders().size();
        Product p4 = new Product(4, "Ergo Stand", new BigDecimal("59.00"));

        orderListRepo.addOrder(p4, 5);

        assertEquals(initialSize + 1, orderListRepo.getOrders().size());
        Order fetched = orderListRepo.getOrderByProductId(4);
        assertNotNull(fetched);
        assertEquals(4, fetched.product().id());
        assertEquals(5, fetched.quantity());
    }

    @Test
    @DisplayName("addOrder should replace an existing order with the same product id when the order id is different")
    void addOrder_updatesExistingWhenSameProductId() {
        int initialSize = orderListRepo.getOrders().size();
        // Same product as p2, but different quantity; repo will generate a new id
        orderListRepo.addOrder(p2, 7);

        // Should not create a duplicate for product id=2
        assertEquals(initialSize, orderListRepo.getOrders().size(), "Should replace, not add duplicate for same product id");
        Order fetchedByProduct = orderListRepo.getOrderByProductId(2);
        assertNotNull(fetchedByProduct);
        assertEquals(7, fetchedByProduct.quantity());
        // Old order with id 102 should be gone
        assertNull(orderListRepo.getOrderById(102));
    }

    @Test
    @DisplayName("removeAllOrders should clear the list of orders")
    void removeAllOrders_clearsTheRepository() {
        assertFalse(orderListRepo.getOrders().isEmpty());
        orderListRepo.removeAllOrders();
        assertTrue(orderListRepo.getOrders().isEmpty());
        assertNull(orderListRepo.getOrderById(101));
        assertNull(orderListRepo.getOrderByProductId(1));
    }

    @Test
    @DisplayName("removeOrderById should remove the order with the given id")
    void removeOrderById_removesOnlyMatchingId() {
        int initialSize = orderListRepo.getOrders().size();

        orderListRepo.removeOrderById(101);

        assertEquals(initialSize - 1, orderListRepo.getOrders().size());
        assertNull(orderListRepo.getOrderById(101));
        // Others remain
        assertNotNull(orderListRepo.getOrderById(102));
        assertNotNull(orderListRepo.getOrderById(103));
    }

    @Test
    @DisplayName("updateOrder should replace an existing order with the same id")
    void updateOrder_replacesByOrderId() {
        // Update existing order 103 by same id, change quantity
        Order updated = new Order(103, p3, 9);

        orderListRepo.updateOrder(updated);

        Order fetched = orderListRepo.getOrderById(103);
        assertNotNull(fetched);
        assertEquals(9, fetched.quantity());
        // Size should remain same
        assertEquals(initialOrders.size(), orderListRepo.getOrders().size());
    }

    @Test
    @DisplayName("updateOrder should replace an existing order with the same product id")
    void updateOrder_replacesByProductIdWhenDifferentOrderId() {
        // Create an order for existing product p1 but new order id
        Order replacement = new Order(999, p1, 4);

        orderListRepo.updateOrder(replacement);

        // Old order with id 101 should be removed and replaced by 999
        assertNull(orderListRepo.getOrderById(101));
        Order fetchedByProduct = orderListRepo.getOrderByProductId(p1.id());
        assertNotNull(fetchedByProduct);
        assertEquals(999, fetchedByProduct.id());
        assertEquals(4, fetchedByProduct.quantity());
        // Count unchanged
        assertEquals(initialOrders.size(), orderListRepo.getOrders().size());
    }

    @Test
    @DisplayName("getOrderById and getOrderByProductId should return the correct order")
    void getOrderById_and_getOrderByProductId_behaveAsExpected() {
        // Positive cases
        Order byId = orderListRepo.getOrderById(102);
        assertNotNull(byId);
        assertEquals(102, byId.id());
        assertEquals(2, byId.product().id());

        Order byProduct = orderListRepo.getOrderByProductId(3);
        assertNotNull(byProduct);
        assertEquals(103, byProduct.id());
        assertEquals(3, byProduct.product().id());

        // Negative cases
        assertNull(orderListRepo.getOrderById(555));
        assertNull(orderListRepo.getOrderByProductId(555));
    }
}
