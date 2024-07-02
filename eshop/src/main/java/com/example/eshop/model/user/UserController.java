package com.example.eshop.model.user;

import com.example.eshop.dto.cart.AddToCartRequest;
import com.example.eshop.dto.cart.CartProductRequest;
import com.example.eshop.model.cart.CartItem;
import com.example.eshop.model.product.Product;
import com.example.eshop.model.product.ProductService;
import jakarta.validation.Valid;
import org.jetbrains.annotations.NotNull;
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

    @GetMapping("/home")
    public String home(){
        return "Home";
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

    // Authentication
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@NotNull @Valid @RequestBody User loginRequest) {
        Optional<User> user = userService.loginUser(loginRequest.getUsername(), loginRequest.getPassword());
        if (user.isPresent()) {
            return ResponseEntity.ok("Login successful");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody User user) {
        try {
            User savedUser = userService.registerUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
        } catch (Exception e) {
            if (e.getMessage().contains("Username or email already exists")) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to register user");
            }
        }
    }

    @GetMapping("/api/private")
    public String privateEndpoint() {
        return "This is a private endpoint!";
    }
}
