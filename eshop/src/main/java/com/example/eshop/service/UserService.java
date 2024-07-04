package com.example.eshop.service;

import com.example.eshop.model.CartItem;
import com.example.eshop.model.Product;
import com.example.eshop.model.User;
import com.example.eshop.repository.UserRepository;
import io.jsonwebtoken.Claims;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepo;

    public Optional<User> getUserByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    public Optional<User> getUserById(String id) {
        return userRepo.findById(id);
    }


    // Cart Services
    @Transactional
    public void addProductToCart(@NotNull User user, Product product, int quantity) {
            CartItem cartItem = new CartItem(product, quantity);
            user.getCart().addItem(cartItem);
            userRepo.save(user);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean reduceProductQuantity(@NotNull User user, String productId){
        if (user.getCart().getItems().isEmpty()){
            return false;
        }
        user.getCart().reduceQuantity(productId);
        userRepo.save(user);
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean removeProductFromCart(User user, String productId){
        if (productExistsInCart(user, productId)){
            user.getCart().removeItem(productId);
            userRepo.save(user);
            return true;
        } else {
            return false;
        }
    }

    @Transactional(readOnly = true)
    public List<CartItem> getUserCartItems(String userId){
        Optional<User> user = userRepo.findById(userId);
        return user.get().getCart().getItems();
    }

    private boolean productExistsInCart(@NotNull User user, String productId) {
        List<CartItem> cartItems = getUserCartItems(user.getId());

        return cartItems.stream()
                .anyMatch(cartItem -> cartItem.getProduct().getId().equals(productId));
    }

    // Authorization Services
    @Transactional(readOnly = true)
    public Optional<User> loginUser(String username, String password) {
        Optional<User> optionalUser = userRepo.findByUsername(username);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (user.getPassword().equals(password)) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    /*@Transactional(rollbackFor = Exception.class)
    public User registerUser(@NotNull User user) throws Exception {
        // Check if username or email already exists
        if (usernameExists(user.getUsername()) || emailExists(user.getEmail())) {
            throw new Exception("Username or email already exists");
        }

        try {
            // Perform your user registration logic here
            return userRepo.save(user);
        } catch (DuplicateKeyException e) {
            if (e.getCode() == 11000) { // Duplicate key error code
                throw new Exception("Username or email already exists");
            } else {
                throw e; // Rethrow the exception if it's not a duplicate key error
            }
        }
    }*/



}