package io.ionic.demo.ecommerce;

import android.app.Application;
import android.content.Context;

import java.util.HashMap;
import java.util.Map;

import io.ionic.demo.ecommerce.data.ShoppingCart;
import io.ionic.demo.ecommerce.data.model.Product;

public class EcommerceApp extends Application {

    private static EcommerceApp instance;

    private ShoppingCart shoppingCart;

    public static EcommerceApp getInstance() {
        return instance;
    }

    public static Context getContext() {
        return instance.getApplicationContext();
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    @Override
    public void onCreate() {
        instance = this;
        super.onCreate();

        // Start app with a fresh shopping cart
        shoppingCart = new ShoppingCart();
    }
}
