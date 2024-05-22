package com.ProvaGrupo.SpringEcommerce.service;

import com.ProvaGrupo.SpringEcommerce.exception.SpringStoreException;
import com.ProvaGrupo.SpringEcommerce.model.Product;
import com.ProvaGrupo.SpringEcommerce.model.ShoppingCart;
import com.ProvaGrupo.SpringEcommerce.model.ShoppingCartItem;
import com.ProvaGrupo.SpringEcommerce.model.Users;
import com.ProvaGrupo.SpringEcommerce.repository.CartRepository;
import com.ProvaGrupo.SpringEcommerce.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CartSevice {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private AuthService authService;

    @Autowired
    private CartRepository cartRepository;

    public void addToCart(String sku){
        Product product = productRepository.findBySku(sku)
                .orElseThrow(() -> new SpringStoreException("Product with SKU : " + sku + " not found"));

        Optional<Users> currentUserOptional = authService.getCurrentUser();

        if (currentUserOptional.isPresent()){
            Users currentUser = currentUserOptional.get();
            ShoppingCart cart = cartRepository.findByUsername(currentUser.getUsername())
                    .orElseGet(() -> {
                        ShoppingCart newCart = new ShoppingCart();
                        newCart.setUsername(currentUser.getUsername());
                        newCart.setCartTotalPrice(BigDecimal.ZERO);
                        newCart.setNumberOfItems(0);
                        return newCart;
                    });

            ShoppingCartItem item = new ShoppingCartItem();
            item.setName(product.getName());
            item.setPrice(product.getPrice());
            item.setShoppingCart(cart);
            cart.getShoppingCartItems().add(item);

            cart.setCartTotalPrice(cart.getShoppingCartItems().stream()
                    .map(ShoppingCartItem :: getPrice)
                    .reduce(BigDecimal.ZERO, BigDecimal :: add));
            cart.setNumberOfItems(cart.getShoppingCartItems().size());

            cartRepository.save(cart);
        }
        else {
            throw new SpringStoreException("User is not authenticated");
        }
    }
}
