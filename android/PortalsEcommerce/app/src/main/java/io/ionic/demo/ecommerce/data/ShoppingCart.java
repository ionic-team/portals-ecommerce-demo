package io.ionic.demo.ecommerce.data;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import io.ionic.demo.ecommerce.data.model.Cart;
import io.ionic.demo.ecommerce.data.model.CartItem;
import io.ionic.demo.ecommerce.data.model.Product;

/**
 * A shopping cart to track items to be purchased.
 */
public class ShoppingCart {

    /**
     * The products and associated quantities in the shopping cart.
     */
    final private Map<Product, Integer> contents;

    /**
     * Constructs a new shopping cart.
     */
    public ShoppingCart() {
        contents = new HashMap<>();
    }

    /**
     * Adds one product to the shopping cart.
     *
     * @param product A product to add to the cart.
     */
    public void addItem(Product product) {
        addItem(product, 1);
    }

    /**
     * Adds multiple of the same product to the shopping cart.
     *
     * @param product A product to add to the cart.
     * @param amount A non-zero, non-negative quantity.
     */
    public void addItem(Product product, int amount) {
        if (amount > 0) {
            if (contents.containsKey(product)) {
                contents.put(product, contents.get(product) + amount);
            } else {
                contents.put(product, amount);
            }
        }
    }

    /**
     * Removes one product from the shopping cart.
     *
     * @param product A product to remove from the cart.
     */
    public void removeItem(Product product) {
        removeItem(product, 1);
    }

    /**
     * Removes multiple of the same product to the shopping cart.
     *
     * @param product A product to remove from the cart.
     * @param amount A non-zero, non-negative quantity.
     */
    public void removeItem(Product product, int amount) {
        if (amount > 0 && contents.containsKey(product)) {
            if (contents.get(product) <= amount) {
                contents.remove(product);
            } else {
                contents.put(product, contents.get(product) - amount);
            }
        }
    }

    public Cart getCart() {
        Cart cart = new Cart();
        cart.id = 1;
        for(Map.Entry<Product, Integer> entry : contents.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            CartItem cartItem = new CartItem();
            cartItem.productId = product.id;
            cartItem.quantity = quantity;
            cart.subTotal += product.price * quantity;
            cart.basket.add(cartItem);
        }
        return cart;
    }

    public void checkout(String result) {
        if (result.equals("success")) {
            this.contents.clear();
        }
    }

    /**
     * Get a string representation of the shopping cart contents.
     *
     * @return A list of products in the cart with their respective quantities.
     */
    @NonNull
    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        for(Map.Entry<Product, Integer> product : contents.entrySet()) {
            output.append(product.getKey().title).append(": ").append(product.getValue()).append("\n");
        }

        return output.toString();
    }

    /**
     * Gets the number of unique products in the cart
     * @return the number of unique products in the cart
     */
    public int getUniqueItemCount() {
        return contents.keySet().size();
    }

    /**
     * Gets the total number of products in the cart
     * @return the total number of products in the cart
     */
    public int getTotalItemCount() {
        int sum = 0;
        Collection<Integer> nums = contents.values();
        for (int num : nums) {
            sum += num;
        }
        return sum;
    }

    /**
     * Get the products in the cart as a Map
     * @return A map of Products and their quantities with the keys being product and the values being the quantities
     */
    public Map<Product, Integer> getProductsInCart() {
        return contents;
    }

    /**
     * Gets the products in the cart as an Array
     * @return An array of unique Products currently in the cart
     */
    public Product[] getProductsInCartAsArray() {
        return contents.keySet().toArray(new Product[0]);
    }

    /**
     * Get the total sum of the Products in the shopping cart
     * @return The total price of the items in the cart
     */
    public float getTotalPriceOfProductsInCart() {
        float sum = 0;
        for(Map.Entry<Product, Integer> product : contents.entrySet()) {
            sum += product.getKey().price * product.getValue();
        }
        return sum;
    }
}
