package com.example.eshop.dto.user;

import com.example.eshop.model.Cart;

public class UserProfileDetailsDTO {

    private String username;
    private String fullname;
    private Cart cart;

    public UserProfileDetailsDTO() {
    }

    public UserProfileDetailsDTO(String username, String fullname, Cart cart) {
        this.username = username;
        this.fullname = fullname;
        this.cart = cart;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }
}
