package org.stefanneuberger.ShopService;

import org.stefanneuberger.OrderListRepo.Order;
import org.stefanneuberger.OrderListRepo.OrderListRepo;
import org.stefanneuberger.productRepo.Product;
import org.stefanneuberger.productRepo.ProductRepo;
import org.stefanneuberger.warehouse.Warehouse;

import java.util.List;

public class ShopService {
    private final int shopId;
    private final String shopName;
    private OrderListRepo orderList = new OrderListRepo();
    private ProductRepo productRepo;
    private Warehouse warehouse;

    public ShopService(int shopId, String shopName) {
        this.shopId = shopId;
        this.shopName = shopName;
        this.productRepo = new ProductRepo();
    }

    public ShopService(int shopId, String shopName, List<Product> initialProducts) {
        this.shopId = shopId;
        this.shopName = shopName;
        this.productRepo = new ProductRepo(initialProducts);
    }

    public ShopService(int shopId, String shopName, Warehouse warehouse, List<Product> allProducts) {
        this.shopId = shopId;
        this.shopName = shopName;
        this.warehouse = warehouse;
        this.productRepo = new ProductRepo(allProducts);
    }

    public int getShopId() {
        return shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public ProductRepo getProductRepo() {
        return productRepo;
    }

    public void setProductRepo(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public OrderListRepo getShopOrders() {
        return orderList;
    }

    public void setShopOrders(List<Order> shopOrders) {
        this.orderList = new OrderListRepo(shopOrders);
    }

    public Order createOrder(Product product, int quantity) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }
        if (productRepo.getProductById(product.id()) == null) {
            throw new IllegalArgumentException("Product does not exist");
        }
        
        // Check warehouse stock if warehouse is available
        if (warehouse != null) {
            int currentStock = warehouse.getStockLevel(product.id());
            if (currentStock < quantity) {
                throw new IllegalArgumentException(
                    String.format("Insufficient stock. Available: %d, Requested: %d",
                        currentStock, quantity)
                );
            }
            // Update the stock by reducing it
            warehouse.updateStock(product.id(), currentStock - quantity);
        }
        
        return orderList.addOrder(product, quantity);    
    }


    public void deleteOrder(int orderId) {
        if (orderId <= 0) {
            throw new IllegalArgumentException("OrderId must be greater than zero");
        }
        orderList.removeOrderById(orderId);
    }

    public void updateOrder(int orderId, int quantity, Product product) {
        if (orderId <= 0) {
            throw new IllegalArgumentException("OrderId must be greater than zero");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        if (productRepo.getProductById(product.id()) == null) {
            throw new IllegalArgumentException("Product does not exist");
        }
        orderList.updateOrder(new Order(orderId, product, quantity));
    }

}
