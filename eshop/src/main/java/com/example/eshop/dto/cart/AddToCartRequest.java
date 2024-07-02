package com.example.eshop.dto.cart;

import com.example.eshop.model.cart.CartItem;
import com.example.eshop.model.user.User;

public class AddToCartRequest {
    private String userId;
    private String productId;
    private int quantity;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
