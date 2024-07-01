package com.example.eshop.cart;

import com.example.eshop.product.Product;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "carts")
public class Cart {
    @Id
    private String id;
    private List<Product> items = new ArrayList<>();


    public void addProduct(Product product) {
        items.add(product);
    }
    // Constructors, getters, and setters

    public Cart() {
    }

    public Cart(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Product> getItems() {
        return items;
    }


}
