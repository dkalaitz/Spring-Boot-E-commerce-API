package com.example.eshop.model.user;

import com.example.eshop.model.cart.Cart;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;

@Document (collection = "users")
public class User {

    @Id
    private String id;

    @Indexed(unique = true)
    @NotNull
    @Size(min = 4, max = 20)
    private String username;

    @Indexed(unique = true)
    @NotNull
    @Email(message = "Email should be valid")
    private String email;

    @NotNull
    @Size(min = 6, max = 20)
    private String password;

    @NotNull
    private String fullName;

    private Cart cart = new Cart(); // Initialize cart explicitly

    // Constructors, getters, and setters

    public User() {
        // Default constructor
    }

    public User(String username, String password, String fullName, String email) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public Cart getCart() {
        return cart;
    }

    public String getId() {
        return id;
    }
}
