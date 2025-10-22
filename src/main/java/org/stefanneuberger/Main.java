package org.stefanneuberger;

import org.stefanneuberger.ShopService.ShopService;
import org.stefanneuberger.productRepo.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Create some sample products
        List<Product> products = new ArrayList<>();
        products.add(new Product(1, "Wireless Mouse", new BigDecimal("29.99")));
        products.add(new Product(2, "Mechanical Keyboard", new BigDecimal("89.99")));
        products.add(new Product(3, "USB-C Hub", new BigDecimal("49.99")));
        products.add(new Product(4, "Gaming Headset", new BigDecimal("79.99")));
        products.add(new Product(5, "Monitor Stand", new BigDecimal("34.99")));
        products.add(new Product(6, "Cable Management Kit", new BigDecimal("19.99")));
        
        // Create a shop service
        ShopService shop = new ShopService(1, "Tech Store", products);
        
        // Start the CLI demo
        ShopCLI shopCLI = new ShopCLI(shop);
        shopCLI.start();
    }
}
