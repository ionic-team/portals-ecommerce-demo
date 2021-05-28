package io.ionic.demo.ecommerce.data;

import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

import io.ionic.demo.ecommerce.data.model.Product;

public class ShoppingCart {

    final private Map<Product, Integer> contents;

    public ShoppingCart() {
        contents = new HashMap<>();
    }

    public void addItem(Product product) {
        addItem(product, 1);
    }

    public void addItem(Product product, int amount) {
        if (amount > 0) {
            if (contents.containsKey(product)) {
                contents.put(product, contents.get(product) + amount);
            } else {
                contents.put(product, amount);
            }
        }
    }

    public void removeItem(Product product) {
        removeItem(product, 1);
    }

    public void removeItem(Product product, int amount) {
        if (amount > 0) {
            if (contents.get(product) <= amount) {
                contents.remove(product);
            } else {
                contents.put(product, contents.get(product) - amount);
            }
        }
    }

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
