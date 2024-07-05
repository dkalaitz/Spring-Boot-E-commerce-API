package com.example.eshop.controllers;

import com.example.eshop.dto.user.UserProfileDetailsDTO;
import com.example.eshop.model.CartItem;
import com.example.eshop.model.Product;
import com.example.eshop.security.jwt.JwtService;
import com.example.eshop.service.ProductService;
import com.example.eshop.model.User;
import com.example.eshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @Autowired
    private JwtService jwtService;

    @GetMapping("/helloWorld")
    public ResponseEntity<String> sayHello(){
        return ResponseEntity.ok("Hello from secured endpoint");
    }

    @GetMapping("/myProfile")
    public UserProfileDetailsDTO getUserProfileDetails(@RequestHeader("Authorization") String authHeader){
        return userService.getUserProfileDetails(jwtService.extractUsername(
                jwtService.extractTokenFromHeader(authHeader)));
    }

    @GetMapping("/myCart")
    public List<CartItem> myCart(@RequestHeader("Authorization") String authHeader) {
        String token = jwtService.extractTokenFromHeader(authHeader);
        String username = jwtService.extractUsername(token);
        return userService.getUserCartItems(username);
    }

    // Cart Interaction APIs
    @PostMapping("/addToCart")
    public ResponseEntity<String> addToCart(@RequestHeader("Authorization") String authHeader,
                                            @RequestParam String productId,
                                            @RequestParam int quantity) {
      try {
          String token = jwtService.extractTokenFromHeader(authHeader);
          String username = jwtService.extractUsername(token);

          // Retrieve user by user username
          Optional<User> user = userService.getUserByUsername(username);
          Optional<Product> product = productService.getProductById(productId);

          // Check if user and exists
          if (user.isPresent() && product.isPresent()) {
              // Add product to user's cart using userService
              userService.addProductToCart(user.get(), product.get(), quantity);
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
    public ResponseEntity<String> removeFromCart(@RequestHeader("Authorization") String authHeader,
                                                 @RequestParam String productId) {
        try {
            String token = jwtService.extractTokenFromHeader(authHeader);
            String username = jwtService.extractUsername(token);
            // Retrieve user by username
            Optional<User> user = userService.getUserByUsername(username);

            // Check if user and exists
            if (user.isPresent()) {
                boolean removed = userService.removeProductFromCart(user.get(), productId);

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
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to remove product to cart: " + e.getMessage());
        }
    }

    @PostMapping("/reduceQuantity")
    public ResponseEntity<String> reduceQuantity(@RequestHeader("Authorization") String authHeader,
                                                 @RequestParam String productId){
        try {
            String token = jwtService.extractTokenFromHeader(authHeader);
            String username = jwtService.extractUsername(token);
            // Retrieve user by username from the request
            Optional<User> user = userService.getUserByUsername(username);

            // Check if user and exists
            if (user.isPresent()) {
                boolean quantityReduced = userService.reduceProductQuantity(user.get(), productId);

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

