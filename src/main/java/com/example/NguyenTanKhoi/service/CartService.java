package com.example.NguyenTanKhoi.service;


import com.example.NguyenTanKhoi.model.CartItem;
import com.example.NguyenTanKhoi.model.Product;
import com.example.NguyenTanKhoi.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.util.ArrayList;
import java.util.List;

@Service
@SessionScope
public class CartService {
    private List<CartItem> cartItems = new ArrayList<>();
    @Autowired
    private ProductRepository productRepository;

    public void addToCart(Long productId, int quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found: " + productId));
        cartItems.add(new CartItem(product, quantity));
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void removeFromCart(Long productId) {
        cartItems.removeIf(item -> item.getProduct().getId().equals(productId));
    }

    public void clearCart() {
        cartItems.clear();
    }

    public void increaseQuantity(Long productId) {
        CartItem item = getCartItemByProductId(productId);
        if (item != null) {
            item.setQuantity(item.getQuantity() + 1);
        }
    }

    public void decreaseQuantity(Long productId) {
        CartItem item = getCartItemByProductId(productId);
        if (item != null && item.getQuantity() > 1) {
            item.setQuantity(item.getQuantity() - 1);
        }
    }

    public CartItem getCartItemByProductId(Long productId) {
        for (CartItem item : cartItems) {
            if (item.getProduct().getId().equals(productId)) {
                return item;
            }
        }
        return null;
    }
}
