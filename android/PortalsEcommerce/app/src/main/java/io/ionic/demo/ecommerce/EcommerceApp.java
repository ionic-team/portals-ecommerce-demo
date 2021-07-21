package io.ionic.demo.ecommerce;

import android.app.Application;
import android.content.Context;

import com.capacitorjs.plugins.camera.CameraPlugin;
import com.getcapacitor.Plugin;

import java.util.Arrays;
import java.util.HashMap;

import io.ionic.demo.ecommerce.data.ShoppingCart;
import io.ionic.demo.ecommerce.plugins.ShopAPIPlugin;
import io.ionic.demo.ecommerce.ui.product.HelpFragment;
import io.ionic.portalslibrary.Portal;
import io.ionic.portalslibrary.PortalManager;

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

//        Portal checkoutPortal = new Portal("checkout");
//        checkoutPortal.setStartDir("webapp");
//        checkoutPortal.setPlugin(ShopAPIPlugin.class);
//
//        Portal helpPortal = new Portal("help");
//        helpPortal.setStartDir("webapp");
//        helpPortal.setPlugin(ShopAPIPlugin.class);
//
//        Portal profilePortal = new Portal("profile");
//        profilePortal.setStartDir("webapp");
//        profilePortal.setPlugin(ShopAPIPlugin.class);
//        profilePortal.setPlugin(CameraPlugin.class);
//
//        PortalManager.addPortal(checkoutPortal);
//        PortalManager.addPortal(helpPortal);
//        PortalManager.addPortal(profilePortal);


        PortalManager.newPortal("checkout")
                .setStartDir("webapp")
                .setPlugins(Arrays.asList(ShopAPIPlugin.class))
                .create();
        HashMap<String, String> initialContext = new HashMap<>();
        initialContext.put("startingRoute", "/help");
        PortalManager.newPortal("help")
                .setStartDir("webapp")
                .setInitialContext(initialContext)
                .setPlugins(Arrays.asList(ShopAPIPlugin.class))
                .create();
        PortalManager.newPortal("webapp")
                .setPlugins(Arrays.asList(ShopAPIPlugin.class, CameraPlugin.class))
                .create();
    }
}
