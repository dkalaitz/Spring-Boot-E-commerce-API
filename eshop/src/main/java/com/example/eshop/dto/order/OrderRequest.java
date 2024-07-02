package com.example.eshop.dto.order;

import java.util.List;

public class OrderRequest {
    private Long userId;
    private List<Long> productIds;
    // Add more fields as needed

    // Getters and setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<Long> getProductIds() {
        return productIds;
    }

    public void setProductIds(List<Long> productIds) {
        this.productIds = productIds;
    }
}
