package org.stefanneuberger.productRepo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductRepoTest {

    private ProductRepo productRepo;
    private List<Product> initialProducts;

    @BeforeEach
    void setUp() {
        Product product1 = new Product(1, "keyboard ZSA voyager", new BigDecimal("123.98"));
        Product product2 = new Product(2, "Mini Mouse", new BigDecimal("22.98"));
        Product product3 = new Product(3, "Mickey Mouse", new BigDecimal("134.98"));
        initialProducts = new ArrayList<>();
        initialProducts.add(product1);
        initialProducts.add(product2);
        initialProducts.add(product3);
        productRepo = new ProductRepo(new ArrayList<>(initialProducts));
    }

    @Test
    void setProducts() {
        // Given a new list of products
        List<Product> newProducts = new ArrayList<>();
        Product newProduct1 = new Product(10, "Trackpad", new BigDecimal("49.99"));
        Product newProduct2 = new Product(11, "USB-C Hub", new BigDecimal("29.99"));
        newProducts.add(newProduct1);
        newProducts.add(newProduct2);

        // When setting the products list
        productRepo.setProducts(newProducts);

        // Then the repo should return the new list
        assertSame(newProducts, productRepo.getProducts(), "Expected the internal list reference to be updated to the new list");
        assertEquals(2, productRepo.getProducts().size());
        assertTrue(productRepo.getProducts().contains(newProduct1));
        assertTrue(productRepo.getProducts().contains(newProduct2));
    }

    @Test
    void getProductById() {
        // Existing product
        Product p2 = productRepo.getProductById(2);
        assertNotNull(p2);
        assertEquals(2, p2.id());
        assertEquals("Mini Mouse", p2.name());
        assertEquals(new BigDecimal("22.98"), p2.price());

        // Non-existing product
        assertNull(productRepo.getProductById(999));
    }

    @Test
    void addProduct() {
        int initialSize = productRepo.getProducts().size();
        Product newProduct = new Product(4, "Ergo Stand", new BigDecimal("59.00"));

        Product returned = productRepo.addProduct(newProduct);

        assertEquals(newProduct, returned, "addProduct should return the same product instance passed in");
        assertEquals(initialSize + 1, productRepo.getProducts().size());
        assertTrue(productRepo.getProducts().contains(newProduct));
    }

    @Test
    void updateProduct() {
        // Update product with id=3
        Product updated = new Product(3, "Mickey Mouse Deluxe", new BigDecimal("199.99"));

        Product returned = productRepo.updateProduct(updated);

        assertEquals(updated, returned, "updateProduct should return the updated product");
        // Ensure only one product with id=3 exists and it equals the updated one
        Product fetched = productRepo.getProductById(3);
        assertNotNull(fetched);
        assertEquals(updated, fetched);

        // Ensure the count remains the same (replace semantics)
        assertEquals(initialProducts.size(), productRepo.getProducts().size());
    }

    @Test
    void deleteProduct() {
        int initialSize = productRepo.getProducts().size();

        productRepo.deleteProduct(1);

        assertEquals(initialSize - 1, productRepo.getProducts().size());
        assertNull(productRepo.getProductById(1));
        // Ensure remaining products still present
        assertNotNull(productRepo.getProductById(2));
        assertNotNull(productRepo.getProductById(3));
    }
}