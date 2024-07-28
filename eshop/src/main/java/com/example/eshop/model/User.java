package com.example.eshop.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Document (collection = "users")
public class User implements UserDetails {

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

    private Cart cart = new Cart();

    private Role role;

    public User(String username, String password, String fullName, String email, Role role) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.email = email;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUsername(@NotNull @Size(min = 4, max = 20) String username) {
        this.username = username;
    }

    public @NotNull @Email(message = "Email should be valid") String getEmail() {
        return email;
    }

    public void setEmail(@NotNull @Email(message = "Email should be valid") String email) {
        this.email = email;
    }

    public void setPassword(@NotNull @Size(min = 6, max = 20) String password) {
        this.password = password;
    }

    public @NotNull String getFullName() {
        return fullName;
    }

    public void setFullName(@NotNull String fullName) {
        this.fullName = fullName;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }
}
