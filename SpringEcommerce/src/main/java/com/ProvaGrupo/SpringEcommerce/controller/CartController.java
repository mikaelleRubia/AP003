package com.ProvaGrupo.SpringEcommerce.controller;

import com.ProvaGrupo.SpringEcommerce.exception.SpringStoreException;
import com.ProvaGrupo.SpringEcommerce.service.CartSevice;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/cart/")
@AllArgsConstructor
public class CartController {

    private final CartSevice cartSevice;

    @PostMapping("/add/{sku}")
    public ResponseEntity<Void> addToCart(@PathVariable String sku) {
        try {
            cartSevice.addToCart(sku);
            return status(HttpStatus.CREATED).build();
        } catch (SpringStoreException e) {
            return status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/remove/{sku}")
    public ResponseEntity<Void> removeFromCart(@PathVariable String sku) {
        try {
            cartSevice.removeFromCart(sku);
            return ResponseEntity.noContent().build();
        } catch (SpringStoreException e) {
            return status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/update/{sku}/{quantity}")
    public ResponseEntity<Void> updateItemQuantityInCart(@PathVariable String sku, @PathVariable int quantity) {
        try {
            cartSevice.updateItemQuantityInCart(sku, quantity);
            return ResponseEntity.ok().build();
        } catch (SpringStoreException e) {
            return status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/clear")
    public ResponseEntity<Void> clearCart(){
        try {
            cartSevice.clearCart();
            return ResponseEntity.noContent().build();
        }catch (SpringStoreException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
