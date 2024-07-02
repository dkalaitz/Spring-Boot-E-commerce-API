package com.example.eshop;

import com.example.eshop.model.product.Product;
import com.example.eshop.model.product.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AppStartupRunner implements CommandLineRunner {

    private final ProductService productService;
    private final Logger logger = LoggerFactory.getLogger(AppStartupRunner.class);

    public AppStartupRunner(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public void run(String... args) throws Exception {
        logger.info("Application started. Executing startup code...");

        if (!productService.isRepoEmpty()){
            return;
        }
        try {
            // Creating 5 product instances
            Product product1 = new Product("iPhone 13", "Smartphone", 999.99, "Latest iPhone model with advanced features", "https://example.com/iphone.jpg");
            Product product2 = new Product("Samsung Galaxy Tab S7", "Tablet", 649.99, "High-performance Android tablet with S Pen", "https://example.com/galaxy-tab.jpg");
            Product product3 = new Product("Dell XPS 15", "Laptop", 1499.99, "Powerful laptop for professional use", "https://example.com/dell-xps.jpg");
            Product product4 = new Product("Sony PlayStation 5", "Gaming Console", 499.99, "Next-gen gaming console with 4K gaming support", "https://example.com/ps5.jpg");
            Product product5 = new Product("Bose QuietComfort 45", "Headphones", 329.99, "Premium noise-canceling headphones with excellent sound quality", "https://example.com/bose-headphones.jpg");

            productService.saveProduct(product1);
            productService.saveProduct(product2);
            productService.saveProduct(product3);
            productService.saveProduct(product4);
            productService.saveProduct(product5);

            logger.info("Product initialization complete.");
        } catch (Exception e) {
            logger.error("Error occurred during product initialization: {}", e.getMessage());
            // Handle or rethrow the exception as needed
            throw e;
        }
    }
}
