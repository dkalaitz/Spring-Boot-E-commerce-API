package com.example.eshop.controllers;

import com.example.eshop.dto.cart.AddToCartRequest;
import com.example.eshop.dto.cart.CartProductRequest;
import com.example.eshop.dto.user.LoginRequest;
import com.example.eshop.model.CartItem;
import com.example.eshop.model.Product;
import com.example.eshop.service.ProductService;
import com.example.eshop.model.User;
import com.example.eshop.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @GetMapping("/helloWorld")
    public ResponseEntity<String> sayHello(){
        return ResponseEntity.ok("Hello from secured endpoint");
    }

    @GetMapping("/myCart")
    public List<CartItem> myCart(@RequestParam String userId) {
        return userService.getUserCartItems(userId);
    }

    // Cart Interaction APIs
    @PostMapping("/addToCart")
    public ResponseEntity<String> addToCart(@RequestBody AddToCartRequest request) {
      try {
          // Retrieve user by user id from the request
          Optional<User> user = userService.getUserById(request.getUserId());
          Optional<Product> product = productService.getProductById(request.getProductId());

          // Check if user and exists
          if (user.isPresent() && product.isPresent()) {
              // Add product to user's cart using userService
              userService.addProductToCart(user.get(), product.get(), request.getQuantity());
              // Return success response
              return ResponseEntity.ok("Product added to cart successfully.");
          } else {
              // Return unauthorized response if user does not exist
              return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User or product not found");
          }
      } catch (Exception e) {
          // Return internal server error response if any exception occurs
          return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add product to cart: " + e.getMessage());
      }
    }

    @PostMapping("/removeFromCart")
    public ResponseEntity<String> removeFromCart(@RequestBody CartProductRequest request) {
        try {
            // Retrieve user by user id from the request
            Optional<User> user = userService.getUserById(request.getUserId());

            // Check if user and exists
            if (user.isPresent()) {
                boolean removed = userService.removeProductFromCart(user.get(), request.getProductId());

                if (removed) {
                    return ResponseEntity.ok("Product removed from cart successfully.");
                } else {
                    return ResponseEntity.badRequest().body("Product not found in cart.");
                }
            } else {
                // Return unauthorized response if user does not exist
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User or product not found");
            }
        } catch (Exception e) {
            // Return internal server error response if any exception occurs
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add product to cart: " + e.getMessage());
        }
    }

    @PostMapping("/reduceQuantity")
    public ResponseEntity<String> reduceQuantity(@RequestBody CartProductRequest request){
        try {
            // Retrieve user by user id from the request
            Optional<User> user = userService.getUserById(request.getUserId());

            // Check if user and exists
            if (user.isPresent()) {
                boolean quantityReduced = userService.reduceProductQuantity(user.get(), request.getProductId());

                if (quantityReduced){
                    return ResponseEntity.ok("Product quantity reduced successfully.");
                } else {
                    return ResponseEntity.badRequest().body("Product not found in cart.");
                }
            } else {
                // Return unauthorized response if user does not exist
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User or product not found");
            }
        } catch (Exception e) {
            // Return internal server error response if any exception occurs
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add product to cart: " + e.getMessage());
        }
    }

}

