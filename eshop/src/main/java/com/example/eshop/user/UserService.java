package com.example.eshop.user;

import com.example.eshop.cart.Cart;
import com.example.eshop.cart.CartRepository;
import com.mongodb.DuplicateKeyException;
import com.mongodb.MongoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private CartRepository cartRepo;

    public Optional<User> loginUser(String username, String password){
        Optional<User> optionalUser = userRepo.findByUsername(username);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (user.getPassword().equals(password)) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    public User registerUser(User user) throws Exception {
        try {
            // Perform your user registration logic here
            return userRepo.save(user);
        } catch (DuplicateKeyException  e) {
            if (e.getCode() == 11000) { // Duplicate key error code
                throw new Exception("Username or email already exists");
            } else {
                throw e; // Rethrow the exception if it's not a duplicate key error
            }
        }
    }
}
