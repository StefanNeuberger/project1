package org.stefanneuberger.OrderListRepo;

import java.util.List;
import java.util.Objects;

public record OrderListRepo(List<Order> orders) {

    public void addOrder(Order order) {
        // make sure there are no duplicate orders
        Order existingOrder = getOrderByProductId(order.product().id());
        if (existingOrder != null) {
            updateOrder(order);
            return;
        }
        orders.add(order);
    }

    public void removeAllOrders() {
        orders.clear();
    }

    public void removeOrderById(int id) {
        orders.removeIf(o -> o.id() == id);
    }

    public void updateOrder(Order order) {
        orders.removeIf(o -> o.id() == order.id() || o.product().id() == order.product().id());
        orders.add(order);
    }

    public Order getOrderById(int id) {
        return orders.stream()
                .filter(o -> o.id() == id)
                .findFirst()
                .orElse(null);
    }

    public Order getOrderByProductId(int productId) {
        return orders.stream()
                .filter(o -> o.product().id() == productId)
                .findFirst()
                .orElse(null);
    }

    @Override
    public String toString() {
        return "OrderListRepo{" +
                "orders=" + orders +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        OrderListRepo that = (OrderListRepo) o;
        return Objects.equals(orders, that.orders);
    }

}
