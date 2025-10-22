package org.stefanneuberger.warehouse;

import org.stefanneuberger.productRepo.Product;

import java.util.HashMap;
import java.util.Map;

public class Warehouse {
    private final Map<Integer, Integer> stock = new HashMap<>();
    private final String warehouseName;
    private final int warehouseId;

    public Warehouse(int warehouseId, String warehouseName) {
        this.warehouseId = warehouseId;
        this.warehouseName = warehouseName;
    }

    public int getWarehouseId() {
        return warehouseId;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void addProduct(Product product, int initialStock) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        if (initialStock < 0) {
            throw new IllegalArgumentException("Initial stock cannot be negative");
        }
        stock.put(product.id(), initialStock);
    }

    public boolean isInStock(int productId) {
        return stock.getOrDefault(productId, 0) > 0;
    }

    public void updateStock(int productId, int newStock) {
        if (newStock < 0) {
            throw new IllegalArgumentException("Stock cannot be negative");
        }
        stock.put(productId, newStock);
    }

    public int getStockLevel(int productId) {
        return stock.getOrDefault(productId, 0);
    }

    @Override
    public String toString() {
        return String.format("Warehouse{id=%d, name='%s', items=%d}",
                warehouseId, warehouseName, stock.size());
    }
}