package com.example.eshop;

import com.example.eshop.product.Product;
import com.example.eshop.product.ProductService;
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
            Product product1 = new Product("Smartphone", 799.99, "High-performance smartphone with dual cameras");
            Product product2 = new Product("Laptop", 1299.99, "Thin and lightweight laptop with SSD storage");
            Product product3 = new Product("Headphones", 149.99, "Wireless headphones with noise-canceling feature");
            Product product4 = new Product("Smart Watch", 199.99, "Fitness tracker with heart rate monitor");
            Product product5 = new Product("Tablet", 499.99, "10-inch tablet with HD display");

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
