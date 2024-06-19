package com.example.NguyenTanKhoi.controller;

import com.example.NguyenTanKhoi.model.CartItem;
import com.example.NguyenTanKhoi.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @GetMapping
    public String showCart(Model model) {
        List<CartItem> cartItems = cartService.getCartItems();

        // Create a map to store unique items with summed quantities
        Map<Long, CartItem> uniqueItems = new HashMap<>();
        double totalPrice = 0;

        for (CartItem item : cartItems) {
            long productId = item.getProduct().getId();
            if (uniqueItems.containsKey(productId)) {
                CartItem existingItem = uniqueItems.get(productId);
                existingItem.setQuantity(existingItem.getQuantity() + item.getQuantity());
            } else {
                uniqueItems.put(productId, item);
            }
            totalPrice += item.getProduct().getPrice() * item.getQuantity();
        }

        model.addAttribute("uniqueItems", uniqueItems.values());
        model.addAttribute("totalPrice", totalPrice);

        return "/cart/cart";
    }

    @PostMapping("/increase/{productId}")
    public String increaseQuantity(@PathVariable Long productId) {
        cartService.increaseQuantity(productId);
        return "redirect:/cart"; // Redirect to the cart page
    }

    @PostMapping("/decrease/{productId}")
    public String decreaseQuantity(@PathVariable Long productId) {
        cartService.decreaseQuantity(productId);
        return "redirect:/cart"; // Redirect to the cart page
    }

    @PostMapping("/add")
    public String addToCart(@RequestParam Long productId, @RequestParam int
            quantity) {
        cartService.addToCart(productId, quantity);
        return "redirect:/cart";
    }

    @GetMapping("/remove/{productId}")
    public String removeFromCart(@PathVariable Long productId) {
        cartService.removeFromCart(productId);
        return "redirect:/cart";
    }

    @GetMapping("/clear")
    public String clearCart() {
        cartService.clearCart();
        return "redirect:/cart";
    }
}
