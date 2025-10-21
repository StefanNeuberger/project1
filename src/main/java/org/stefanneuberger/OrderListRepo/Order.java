package org.stefanneuberger.OrderListRepo;

import org.stefanneuberger.productRepo.Product;

public record Order(int id, Product product, int quantity) {
}
