package com.example.eshop.model.cart;

import com.example.eshop.model.product.Product;
import java.util.ArrayList;
import java.util.List;

public class Cart {

    private List<CartItem> items = new ArrayList<>();

    // Default constructor
    public Cart() {
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void addItem(CartItem item) {
        boolean found = false;
        for (CartItem cartItem : items) {
            Product cartProduct = cartItem.getProduct();
            Product itemProduct = item.getProduct();

            if (cartProduct != null && itemProduct != null &&
                    cartProduct.getId() != null && cartProduct.getId().equals(itemProduct.getId())) {
                // If the product already exists in the cart, increase the quantity
                cartItem.setQuantity(cartItem.getQuantity() + item.getQuantity());
                found = true;
                break;
            }
        }
        if (!found) {
            // If the product does not exist in the cart, add it as a new item
            items.add(item);
        }
    }


    public void removeItem(CartItem item) {
        Product itemProduct = item.getProduct();
        if (itemProduct != null) {
            items.removeIf(cartItem -> cartItem.getProduct().getId().equals(itemProduct.getId()));
        }
    }

    public void addQuantity(Product product, int quantity) {
        if (product != null && product.getId() != null) {
            boolean found = false;
            for (CartItem item : items) {
                Product cartProduct = item.getProduct();
                if (cartProduct != null && product.getId().equals(cartProduct.getId())) {
                    item.setQuantity(item.getQuantity() + quantity);
                    found = true;
                    break;
                }
            }
            if (!found) {
                items.add(new CartItem(product, quantity));
            }
        }
    }

    public void reduceQuantity(String productId) {
        for (CartItem item : items) {
            if (item.getProduct() != null && productId.equals(item.getProduct().getId())) {
                item.setQuantity(item.getQuantity() - 1);
                if (item.getQuantity() <= 0) {
                    items.remove(item);
                }
                break;
            }
        }
    }

    public void removeItem(String productId) {
        items.removeIf(item -> item.getProduct() != null && productId.equals(item.getProduct().getId()));
    }
}
