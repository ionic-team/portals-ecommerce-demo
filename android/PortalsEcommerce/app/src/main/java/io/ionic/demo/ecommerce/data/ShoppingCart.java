package io.ionic.demo.ecommerce.data;

import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

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
}
