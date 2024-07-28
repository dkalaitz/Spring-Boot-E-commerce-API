package com.example.eshop.service;

import com.example.eshop.model.Product;
import com.example.eshop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

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
        if (searchTerm != null && !searchTerm.isEmpty()) {
            Pattern pattern = Pattern.compile(searchTerm, Pattern.CASE_INSENSITIVE);
            return productRepository.findByNameRegex(pattern);
        }
        return null;
    }

}

