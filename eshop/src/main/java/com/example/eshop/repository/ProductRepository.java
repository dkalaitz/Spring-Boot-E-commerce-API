package com.example.eshop.repository;

import com.example.eshop.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.regex.Pattern;

public interface ProductRepository extends MongoRepository<Product, String> {
    List<Product> findByType(String type);
    List<Product> findByNameRegex(Pattern pattern);
}
