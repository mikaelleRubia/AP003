package com.ProvaGrupo.SpringEcommerce.service;

import com.ProvaGrupo.SpringEcommerce.exception.SpringStoreException;
import com.ProvaGrupo.SpringEcommerce.model.*;
import com.ProvaGrupo.SpringEcommerce.repository.CartRepository;
import com.ProvaGrupo.SpringEcommerce.repository.ProductRepository;
import com.ProvaGrupo.SpringEcommerce.repository.ShoppingCartItemRepository;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import org.mockito.ArgumentCaptor;

@ExtendWith(MockitoExtension.class)

public class CartSeviceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private AuthService authService;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private ShoppingCartItemRepository shoppingCartItemRepository;

    @InjectMocks
    private CartSevice cartSevice;

    private Faker faker = new Faker(new Locale("en-US"));
    private static final Logger log = LoggerFactory.getLogger(CartSeviceTest.class);

    private Product product;
    private Users user;
    private ShoppingCart cart;
    private ShoppingCartItem shoppingCartItem;

    @BeforeEach
    public void setUp(){
        Category category = Category.builder()
                .name("Electronics")
                .possibleFacets(new ArrayList<>())
                .build();

        product = Product.builder()
                .name(faker.commerce().productName())
                .description("Example product description")
                .price(new BigDecimal("99.99"))
                .sku(faker.regexify("[a-zA-Z0-9]{2,50}"))
                .imageUrl("https://example.com/image.jpg")
                .category(category)
                .quantity(10)
                .manufacturer("Example Manufacturer")
                .featured(true)
                .build();

        String password = faker.internet().password(8, 16, true, true, true);

        user = Users.builder().id(1L).email(faker.internet().emailAddress())
                .username(faker.regexify("[a-zA-Z0-9]{5,16}")).password(password).passwordConfirmation(password)
                .enabled(true).build();

        cart = ShoppingCart.builder()
                .username(user.getUsername())
                .cartTotalPrice(BigDecimal.ZERO)
                .numberOfItems(0)
                .shoppingCartItems(new ArrayList<>())
                .build();

        shoppingCartItem = ShoppingCartItem.builder()
                .Id(1L)
                .name("Example Item")
                .price(new BigDecimal("99.99"))
                .shoppingCart(cart)
                .product(product)
                .build();
    }

    @Test
    public void testAddToCartProductNotFound(){
        log.info("--- Running AddToCartProductNotFound ---\n");
        when(productRepository.findBySku(anyString())).thenReturn(Optional.empty());
        SpringStoreException exception = assertThrows(SpringStoreException.class , () -> {
            log.debug("Attempting to add product with non-existent SKU to cart");
            cartSevice.addToCart("non existent sku");
        });
        log.error("Product not found exception thrown: {}", exception.getMessage());
        assertEquals("Product with SKU : non existent sku not found", exception.getMessage());
        log.info("Completed test: testAddToCartProductNotFound");
    }

    @Test
    public void testAddToCartNewCartCreated(){
        log.info("--- Running AddToCartNewCartCreated ---\n");
        when(productRepository.findBySku(anyString())).thenReturn(Optional.of(product));
        when(authService.getCurrentUser()).thenReturn(Optional.of(user));
        when(cartRepository.findByUsername(anyString())).thenReturn(Optional.empty());

        log.debug("Adding product to a new cart");
        cartSevice.addToCart(product.getSku());

        ArgumentCaptor<ShoppingCart> cartCaptor = ArgumentCaptor.forClass(ShoppingCart.class);
        verify(cartRepository, times(1)).save(cartCaptor.capture());
        ShoppingCart savedCart = cartCaptor.getValue();

        log.info("Verifying that the cart was saved");
        log.info("Saved cart: {}", savedCart);

        assertNotNull(savedCart, "Saved cart shouldn't be null");
        assertEquals(user.getUsername(), savedCart.getUsername(), "Cart's username should match the user's username");
        assertEquals(1, savedCart.getNumberOfItems(), "Cart should have one item");
        assertEquals(product.getPrice(), savedCart.getCartTotalPrice(), "Cart's total price should match the product's price");
        assertEquals(1, savedCart.getShoppingCartItems().size(), "Cart should contain 1 shopping cart item");
        assertEquals(product.getName(), savedCart.getShoppingCartItems().getFirst().getName(), "Cart item name should match the product name");
        assertEquals(product.getPrice(), savedCart.getShoppingCartItems().getFirst().getPrice(), "Cart item price should match the product price");

        log.info("Completed test: testAddToCartNewCartCreated");
    }

    @Test
    public void testAddToCartExistingCartUpdated(){
        log.info("--- Running AddToCartExistingCartUpdated ---\n");
        when(productRepository.findBySku(anyString())).thenReturn(Optional.of(product));
        when(authService.getCurrentUser()).thenReturn(Optional.of(user));
        when(cartRepository.findByUsername(anyString())).thenReturn(Optional.of(cart));

        Category category = Category.builder()
                .name("Electronics")
                .possibleFacets(new ArrayList<>())
                .build();

        Product newProduct = Product.builder()
                    .name(faker.commerce().productName())
                    .description("Example product description")
                    .price(new BigDecimal("10.00"))
                    .sku(faker.regexify("[a-zA-Z0-9]{2,50}"))
                    .imageUrl("https://example.com/image.jpg")
                    .category(category)
                    .build();

        when(productRepository.findBySku(newProduct.getSku())).thenReturn(Optional.of(newProduct));

        log.debug("Adding product to an existing cart");
        cartSevice.addToCart(product.getSku());
        cartSevice.addToCart(newProduct.getSku());

        log.info("Verifying that the cart was updated and saved");
        ArgumentCaptor<ShoppingCart> cartCaptor = ArgumentCaptor.forClass(ShoppingCart.class);
        verify(cartRepository, times(2)).save(cartCaptor.capture());

        List<ShoppingCart> savedCarts = cartCaptor.getAllValues();

        ShoppingCart finalCart = savedCarts.get(savedCarts.size() - 1);
        log.info("Final cart after adding both products: {}", finalCart);

        assertNotNull(finalCart, "Final cart shouldn't be null");
        assertEquals(user.getUsername(), finalCart.getUsername(), "Cart's username should match the user's username");
        assertEquals(2, finalCart.getNumberOfItems(), "Cart should have two items");
        assertEquals(product.getPrice().add(newProduct.getPrice()), finalCart.getCartTotalPrice(), "Cart's total price should match the sum of the products' prices");

        log.info("Completed test: testAddToCartExistingCartUpdated");
    }

    @Test
    public void testRemoveItemFromCart(){
        log.info("--- Running RemoveItemFromCart ---\n");
        when(productRepository.findBySku(anyString())).thenReturn(Optional.of(product));
        when(authService.getCurrentUser()).thenReturn(Optional.of(user));
        when(cartRepository.findByUsername(anyString())).thenReturn(Optional.of(cart));
        when(shoppingCartItemRepository.findByShoppingCartIdAndProductId(cart.getId(), product.getId()))
                .thenReturn(Optional.of(shoppingCartItem));

        log.debug("Removing product from the cart");
        cartSevice.removeFromCart(product.getSku());

        verify(shoppingCartItemRepository, times(1)).delete(shoppingCartItem); // Verifica se o método delete foi chamado uma vez com o item de carrinho fornecido
        verify(cartRepository, times(1)).save(cart);

        log.info("Completed test: testRemoveItemFromCart");
    }
}
