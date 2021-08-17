package io.ionic.demo.ecommerce;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import com.capacitorjs.plugins.camera.CameraPlugin;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import io.ionic.demo.ecommerce.data.ShoppingCart;
import io.ionic.demo.ecommerce.plugins.ShopAPIPlugin;
import io.ionic.demo.ecommerce.portals.FadePortalFragment;
import io.ionic.portals.LiveUpdate;
import io.ionic.portals.LiveUpdateBuilder;
import io.ionic.portals.LiveUpdateManager;
import io.ionic.portals.Portal;
import io.ionic.portals.PortalManager;
import io.ionic.portals.PortalsPlugin;

/**
 * The parent Application Class for the E-Commerce app.
 */
public class EcommerceApp extends Application implements LifecycleObserver {

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

        // Checkout Portal
        PortalManager.newPortal("checkout")
                .setStartDir("webapp")
                .setPlugins(Arrays.asList(ShopAPIPlugin.class, PortalsPlugin.class))
                .create();

        // Help Portal
        HashMap<String, String> initialContext = new HashMap<>();
        initialContext.put("startingRoute", "/help");

        LiveUpdate helpLiveUpdate = new LiveUpdateBuilder("f76eb498")
                .setChannel("Production")
                .create();

        PortalManager.newPortal("help")
                .setStartDir("webapp")
                .setInitialContext(initialContext)
                .setPlugins(Arrays.asList(ShopAPIPlugin.class))
                .setPortalFragmentType(FadePortalFragment.class)
                .setLiveUpdate(helpLiveUpdate)
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

        // Todo: remove. Temporarily save some test values to sharedprefs to test checkUpdate
        saveVals();

        LiveUpdateManager.sync(getApplicationContext(), PortalManager.getPortals());
    }

    private void saveVals() {
        String CHANNEL = "CH_";
        String BINARY_VERSION = "BINV_";
        String CURRENT_VERSION_ID = "CVID_";
        String CURRENT_BUILD_ID = "CBID_";

        for (Map.Entry<String, Portal> entry : PortalManager.getPortals().entrySet()) {
            Portal portal = entry.getValue();
            if (portal.getLiveUpdate() != null) {
                LiveUpdate liveUpdate = portal.getLiveUpdate();
                String appId = liveUpdate.getAppId();

                SharedPreferences sharedPreferences = getSharedPreferences("ionicDeploySavedPreferences", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(CHANNEL + appId, liveUpdate.getChannel());
                editor.putString(BINARY_VERSION + appId, BuildConfig.VERSION_NAME);
                //editor.putString(CURRENT_VERSION_ID + appId, "fb05009d-8b53-4ba8-85c8-c7a276255b90");
                //editor.putString(CURRENT_BUILD_ID + appId, "7522169");
                editor.apply();
            }
        }
    }
}
