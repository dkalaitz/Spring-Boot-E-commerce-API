package com.example.eshop.service;

import com.example.eshop.dto.user.UserProfileDetailsDTO;
import com.example.eshop.model.CartItem;
import com.example.eshop.model.Product;
import com.example.eshop.model.User;
import com.example.eshop.repository.UserRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public boolean removeProductFromCart(@NotNull User user, String productId){
        if (productExistsInCart(user, productId)){
            user.getCart().removeItem(productId);
            userRepo.save(user);
            return true;
        } else {
            return false;
        }
    }

    @Transactional(readOnly = true)
    public List<CartItem> getUserCartItems(String username){
        Optional<User> user = userRepo.findByUsername(username);
        return user.get().getCart().getItems();
    }



    public UserProfileDetailsDTO getUserProfileDetails(String username) {
        Optional<User> user = userRepo.findByUsername(username);
        UserProfileDetailsDTO userProfile = new UserProfileDetailsDTO(user.get().getUsername(),
                user.get().getFullName(),
                user.get().getCart());
        return userProfile;
    }

    private boolean productExistsInCart(@NotNull User user, String productId) {
        List<CartItem> cartItems = getUserCartItems(user.getUsername());

        return cartItems.stream()
                .anyMatch(cartItem -> cartItem.getProduct().getId().equals(productId));
    }

}