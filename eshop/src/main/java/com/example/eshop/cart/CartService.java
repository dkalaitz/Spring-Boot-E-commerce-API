package com.example.eshop.cart;

import com.example.eshop.product.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepo;

    public void addProductToCart(String cartId, Product product){
        Cart cart = cartRepo.findById(cartId).orElseThrow(() -> new RuntimeException("Cart not found"));

        // Add the product to the cart
        cart.addProduct(product);

        // Save the updated cart
        cartRepo.save(cart);
    }
}
