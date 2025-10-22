package org.stefanneuberger.OrderListRepo;

import org.stefanneuberger.productRepo.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OrderListRepo implements OrderRepoSpec {

    private final List<Order> orders = new ArrayList<>();

    public OrderListRepo() {
    }

    public OrderListRepo(List<Order> orders) {
        this.orders.addAll(orders);
    }

    public int getNextId() {
        return orders.stream()
                .mapToInt(Order::id)
                .max()
                .orElse(0) + 1;
    }

    public void addOrder(Product product, int quantity) {
        Order order = new Order(getNextId(), product, quantity);
        // make sure there are no duplicate orders
        Order existingOrder = getOrderByProductId(order.product().id());
        if (existingOrder != null) {
            updateOrder(order);
            return;
        }
        orders.add(order);
    }

    @Override
    public void addOrder(Order order) {
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

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders.clear();
        this.orders.addAll(orders);
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

    @Override
    public int hashCode() {
        return Objects.hash(orders);
    }

}
