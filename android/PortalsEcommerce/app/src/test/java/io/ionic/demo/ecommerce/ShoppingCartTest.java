package io.ionic.demo.ecommerce;

import com.getcapacitor.PluginCall;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import io.ionic.demo.ecommerce.data.ShoppingCart;
import io.ionic.demo.ecommerce.data.model.Cart;
import io.ionic.demo.ecommerce.data.model.CartItem;
import io.ionic.demo.ecommerce.data.model.Product;

import static org.junit.Assert.*;

public class ShoppingCartTest {

    ShoppingCart shoppingCart;

    @Before
    public void beforeEach() {
        shoppingCart = new ShoppingCart();
    }

    @Test
    public void getCartShouldReturnCart() {
        Cart cart = shoppingCart.getCart();

        assertNotNull(cart);
    }

    @Test
    public void addingItemToCartShouldCalcSubTotal() {
        Product product = new Product();
        product.id = 1;
        product.price = 5;
        shoppingCart.addItem(product, 1);
        Cart cart = shoppingCart.getCart();

        assertEquals(5, cart.subTotal, 0);
    }

    @Test
    public void addingTwoOfSameItemToCartShouldCalcSubTotal() {
        Product product = new Product();
        product.id = 1;
        product.price = 5;
        shoppingCart.addItem(product, 2);
        Cart cart = shoppingCart.getCart();

        assertEquals(10, cart.subTotal, 0);
    }

    @Test
    public void addingTwoOfSameItemToCartShouldBeInBasket() {
        Product product = new Product();
        product.id = 1;
        product.price = 5;
        shoppingCart.addItem(product, 2);
        Cart cart = shoppingCart.getCart();

        assertEquals(1, cart.basket.get(0).productId);
        assertEquals(2, cart.basket.get(0).quantity);
    }

    @Test public void callingCheckoutWithSuccessShouldClearCart() {
        Product product = new Product();
        product.id = 1;
        product.price = 5;
        shoppingCart.addItem(product, 2);
        Cart cart = shoppingCart.getCart();

        assertEquals(1, cart.basket.size());
        assertEquals(10, cart.subTotal, 0);

        shoppingCart.checkout("success");
        cart = shoppingCart.getCart();

        assertEquals(0, cart.basket.size());
        assertEquals(0, cart.subTotal, 0);
    }
}
