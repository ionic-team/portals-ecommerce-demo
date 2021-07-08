package io.ionic.demo.ecommerce;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.getcapacitor.Plugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PortalManager {
    private static final List<Class<? extends Plugin>> initialPlugins = new ArrayList<>();
    private static final Map<String, Portal> portals = new HashMap<>();

    private PortalManager() {

    }

    public static void addPlugin(Class<? extends Plugin> plugin) {
        initialPlugins.add(plugin);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void addPortal(String name, String startingDir) {
        Portal portal = new Portal(startingDir);
        portals.put(name, portal);
    }

    public static Portal getPortal(String name) {
        Portal portal =  portals.get(name);
        portal.setPlugins(initialPlugins);
        return portal;
    }

//    @RequiresApi(api = Build.VERSION_CODES.N)
//    public static Fragment getFragment(String name) {
//        Portal portal = getPortal(name);
//        initialPlugins.forEach(portal::addPlugin);
//        return portal.getFragment();
//    }

}
