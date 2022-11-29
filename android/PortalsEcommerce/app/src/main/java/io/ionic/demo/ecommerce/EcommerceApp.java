package io.ionic.demo.ecommerce;

import android.app.Application;
import android.content.Context;

import com.capacitorjs.plugins.camera.CameraPlugin;

import java.util.Arrays;
import java.util.HashMap;

import io.ionic.demo.ecommerce.data.ShoppingCart;
import io.ionic.demo.ecommerce.plugins.ShopAPIPlugin;
import io.ionic.demo.ecommerce.portals.FadePortalFragment;
import io.ionic.portals.PortalManager;
import io.ionic.portals.PortalsPlugin;

/**
 * The parent Application Class for the E-Commerce app.
 */
public class EcommerceApp extends Application {

    /**
     * A single instance of this class.
     */
    private static EcommerceApp instance;

    /**
     * The active shopping cart used for this shopping session.
     */
    private ShoppingCart shoppingCart;

    /**
     * Get the singleton instance of the app class.
     *
     * @return A singleton instance of the app class.
     */
    public static EcommerceApp getInstance() {
        return instance;
    }

    /**
     * Gets the application context from the singleton instance of the app class.
     *
     * @return The application context.
     */
    public static Context getContext() {
        return instance.getApplicationContext();
    }

    /**
     * Get the active shopping cart state used for this shopping session.
     *
     * @return The shopping cart for the current shopping session.
     */
    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    /**
     * Saves a reference to the application object on app launch and creates a fresh
     * shopping cart to be used for the shopping session.
     */
    @Override
    public void onCreate() {
        instance = this;
        super.onCreate();

        // Start app with a fresh shopping cart
        shoppingCart = new ShoppingCart();

        // Register Portals
        PortalManager.register("eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiIzMDU2ZjBlNC1kYTFkLTQ1YWMtYWJjZi1hNDg3MzMyZTQwNGYifQ.0H_gnwXCL1Z-GtFCwQ3J9YrybMxQO56CYo3PFGzoueB56DMvKT4jiQhLzhDYKEE5GwlqX-r0H_qklYKg_jtMyK9QZ_-kTNWi6LyjrJTcgFVwxjz27PZaqZPoKWyJLotSIbBhN8BF5flunCGW8kWL4_nY6FUmswatPDgcvyPmOydr9InbEHHDUVvi9mGwy_G78BjDrl9bThezpGRseBTOI7KH5FUdXwH9DCZJ2RC4_ukTNKMqaKFh-OcD8KDBUIdSP8GE0quO7zL4qSINvxMMzpupTdQKf3Td5B1mvLlrS4kF_8VPoQtvB8JqMrmH2fa8f31fCiz1EV4Wkngb_5yC7w");

        // Checkout Portal
        PortalManager.newPortal("checkout")
                .setStartDir("webapp")
                .setPlugins(Arrays.asList(ShopAPIPlugin.class))
                .create();

        // Help Portal
        HashMap<String, String> initialContext = new HashMap<>();
        initialContext.put("startingRoute", "/help");
        PortalManager.newPortal("help")
                .setStartDir("webapp")
                .setInitialContext(initialContext)
                .setPlugins(Arrays.asList(ShopAPIPlugin.class))
                .setPortalFragmentType(FadePortalFragment.class)
                .create();

        // Profile Portal
        HashMap<String, String> initialContextProfile = new HashMap<>();
        initialContextProfile.put("startingRoute", "/user");
        PortalManager.newPortal("profile")
                .setStartDir("webapp")
                .addPlugin(ShopAPIPlugin.class)
                .addPlugin(CameraPlugin.class)
                .setInitialContext(initialContextProfile)
                .create();
    }
}
