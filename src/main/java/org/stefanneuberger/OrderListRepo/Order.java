package org.stefanneuberger.OrderListRepo;

import org.stefanneuberger.productRepo.Product;

import java.math.BigDecimal;

public record Order(int id, Product product, int quantity) {

    public BigDecimal totalPrice() {
        if (product == null || product.price() == null) {
            return BigDecimal.ZERO;
        }
        return product.price().multiply(BigDecimal.valueOf(quantity));
    }
}
