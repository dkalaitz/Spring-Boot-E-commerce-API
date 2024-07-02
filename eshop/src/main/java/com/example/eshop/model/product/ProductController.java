package com.example.eshop.model.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/getTypeProducts")
    public List<Product> getTypeProducts(@RequestParam String type){
        return productService.getTypeProducts(type);
    }

    @GetMapping("/searchProduct")
    public List<Product> getSearchedProducts(@RequestParam String searchTerm){
        return productService.getSearchedProducts(searchTerm);
    }

    @GetMapping("/getAllProducts")
    public List<Product> getAllProducts(){
        return productService.getAllProducts();
    }

    @GetMapping("/getProduct")
    public Optional<Product> getProduct(@RequestParam String productId){
        return productService.getProductById(productId);
    }

    @PostMapping("/addProduct")
    public void saveProduct(@RequestBody Product product){
        productService.saveProduct(product);
    }

    @PostMapping("/deleteProduct")
    public void deleteProduct(@RequestParam String productId){
        productService.deleteProduct(productId);
    }
}
