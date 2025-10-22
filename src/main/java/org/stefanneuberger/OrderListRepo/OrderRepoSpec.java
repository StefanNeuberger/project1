package org.stefanneuberger.OrderListRepo;

import java.util.List;

public interface OrderRepoSpec {
    void addOrder(Order order);

    void removeAllOrders();

    void removeOrderById(int id);

    void updateOrder(Order order);

    Order getOrderById(int id);

    Order getOrderByProductId(int productId);

    List<Order> getOrders();
}
