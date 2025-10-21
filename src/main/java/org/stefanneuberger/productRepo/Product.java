package org.stefanneuberger.productRepo;

import java.math.BigDecimal;

public record Product(int id, String name, BigDecimal price) {
}
