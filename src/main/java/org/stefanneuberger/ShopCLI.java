package org.stefanneuberger;

import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;
import org.stefanneuberger.OrderListRepo.Order;
import org.stefanneuberger.ShopService.ShopService;
import org.stefanneuberger.productRepo.Product;

import java.util.List;
import java.util.Scanner;


public class ShopCLI {

    private final ShopService shopService;
    private final Scanner scanner;
    private boolean running = true;

    public ShopCLI(ShopService shopService) {
        this.shopService = shopService;
        this.scanner = new Scanner(System.in);
        AnsiConsole.systemInstall(); // Enable ANSI color support
    }

    public void start() {
        System.out.println(Ansi.ansi().fg(Ansi.Color.CYAN).a("🛒 Welcome to " + shopService.getShopName() + "! 🛒").reset());
        System.out.println(Ansi.ansi().fg(Ansi.Color.YELLOW).a("Enter product number to select, 'q' to quit").reset());
        System.out.println();

        while (running) {
            displayProducts();
            handleInput();
        }

        System.out.println(Ansi.ansi().fg(Ansi.Color.GREEN).a("Thank you for shopping with us! 👋").reset());
        AnsiConsole.systemUninstall();
    }

    private void displayProducts() {
        // Clear screen
        System.out.print(Ansi.ansi().eraseScreen().cursor(0, 0));

        System.out.println(Ansi.ansi().fg(Ansi.Color.CYAN).a("🛒 " + shopService.getShopName() + " - Product Selection").reset());
        System.out.println(Ansi.ansi().fg(Ansi.Color.YELLOW).a("Enter product number to select, 'q' to quit").reset());
        System.out.println();

        List<Product> products = shopService.getProductRepo().getProducts();

        if (products.isEmpty()) {
            System.out.println(Ansi.ansi().fg(Ansi.Color.RED).a("No products available!").reset());
            return;
        }

        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
            String productLine = String.format("%d. %s - %s €",
                    i + 1, product.name(), product.price());
            System.out.println(Ansi.ansi().fg(Ansi.Color.WHITE).a(productLine).reset());
        }

        System.out.println();
        System.out.println(Ansi.ansi().fg(Ansi.Color.GREEN).a("Enter product number to select").reset());
    }

    private void handleInput() {
        System.out.print(Ansi.ansi().fg(Ansi.Color.CYAN).a("Enter product number (1-" + shopService.getProductRepo().getProducts().size() +
                ") or 'q' to quit: ").reset());

        String input = scanner.nextLine().trim();

        if ("q".equalsIgnoreCase(input) || "quit".equalsIgnoreCase(input)) {
            running = false;
            return;
        }

        try {
            int productNumber = Integer.parseInt(input);
            if (productNumber >= 1 && productNumber <= shopService.getProductRepo().getProducts().size()) {
                selectProduct(productNumber - 1);
            } else {
                System.out.println(Ansi.ansi().fg(Ansi.Color.RED).a("Invalid product number!").reset());
                pause();
            }
        } catch (NumberFormatException e) {
            System.out.println(Ansi.ansi().fg(Ansi.Color.RED).a("Please enter a valid number!").reset());
            pause();
        }
    }

    private void selectProduct(int productIndex) {
        Product selectedProduct = shopService.getProductRepo().getProducts().get(productIndex);

        // Clear screen
        System.out.print(Ansi.ansi().eraseScreen().cursor(0, 0));

        System.out.println(Ansi.ansi().fg(Ansi.Color.GREEN).a("✅ Selected: " + selectedProduct.name()).reset());
        System.out.println(Ansi.ansi().fg(Ansi.Color.WHITE).a("Price: " + selectedProduct.price() + " €").reset());
        System.out.println();

        // Get quantity
        int quantity = getQuantity();

        if (quantity > 0) {
            try {
                // Create order
                Order createdOrder = shopService.createOrder(selectedProduct, quantity);

                if (createdOrder != null) {
                    displayOrderSuccess(createdOrder);
                    running = false;
                }

            } catch (IllegalArgumentException e) {
                System.out.println(Ansi.ansi().fg(Ansi.Color.RED).a("Error: " + e.getMessage()).reset());
                pause();
            }
        }
    }

    private int getQuantity() {
        while (true) {
            System.out.print(Ansi.ansi().fg(Ansi.Color.CYAN).a("Enter quantity (or '0' to cancel): ").reset());

            try {
                String input = scanner.nextLine().trim();
                int quantity = Integer.parseInt(input);

                if (quantity < 0) {
                    System.out.println(Ansi.ansi().fg(Ansi.Color.RED).a("Quantity must be positive!").reset());
                    continue;
                }

                return quantity;

            } catch (NumberFormatException e) {
                System.out.println(Ansi.ansi().fg(Ansi.Color.RED).a("Please enter a valid number!").reset());
            }
        }
    }

    private void displayOrderSuccess(Order order) {
        System.out.println();
        System.out.println(Ansi.ansi().fg(Ansi.Color.GREEN).a("🎉 Order Created Successfully! 🎉").reset());
        System.out.println();

        String orderDetails = String.format(
                "| Order ID: %d%n" +
                        "| Product: %s%n" +
                        "| Quantity: %d%n" +
                        "| Unit Price: %s €%n" +
                        "| Total: %s €%n",
                order.id(),
                order.product().name(),
                order.quantity(),
                order.product().price(),
                order.totalPrice()
        );

        String[] lines = orderDetails.split("\n");
        for (String line : lines) {
            System.out.println(line);
        }

        System.out.println();
    }

    private void pause() {
        System.out.println(Ansi.ansi().fg(Ansi.Color.YELLOW).a("Press Enter to continue...").reset());
        scanner.nextLine();
    }
}