package com.example.eshop.model.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(String id) {
        return productRepository.findById(id);
    }

    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    public void deleteProduct(String id) {
        productRepository.deleteById(id);
    }

    public boolean isRepoEmpty(){
        return productRepository.count() == 0;
    }

    public List<Product> getTypeProducts(String type) {
        return productRepository.findByType(type);
    }

    public List<Product> getSearchedProducts(String searchTerm) {
        // Modify searchTerm to be a valid regex pattern (case-insensitive)
        String regexPattern = ".*" + searchTerm + ".*";
        return productRepository.findByNameRegex(regexPattern);
    }

}

