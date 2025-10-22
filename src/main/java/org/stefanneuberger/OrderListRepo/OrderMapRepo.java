package org.stefanneuberger.OrderListRepo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderMapRepo implements OrderRepoSpec {

    private Map<Integer, Order> orders = new HashMap<>();

    public OrderMapRepo(Map<Integer, Order> orders) {
        this.orders = orders;
    }

    @Override
    public void addOrder(Order order) {
        orders.put(order.id(), order);
    }

    @Override
    public void removeAllOrders() {
        orders.clear();
    }

    @Override
    public void removeOrderById(int id) {
        orders.remove(id);
    }

    @Override
    public void updateOrder(Order order) {
        orders.put(order.id(), order);
    }

    @Override
    public Order getOrderById(int id) {
        if (orders.containsKey(id)) {
            return orders.get(id);
        }
        return null;
    }

    @Override
    public Order getOrderByProductId(int productId) {
        for (Order order : orders.values()) {
            if (order.product().id() == productId) {
                return order;
            }
        }
        return null;
    }

    @Override
    public List<Order> getOrders() {
        return List.copyOf(orders.values());
    }
}
