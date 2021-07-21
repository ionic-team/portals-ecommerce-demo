package io.ionic.demo.ecommerce;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.getcapacitor.Plugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PortalManagerOld {
    private static final List<Class<? extends Plugin>> initialPlugins = new ArrayList<>();
    private static final Map<String, PortalOld> portals = new HashMap<>();

    private PortalManagerOld() {

    }

    public static void addPlugin(Class<? extends Plugin> plugin) {
        initialPlugins.add(plugin);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void addPortal(String name, String startingDir) {
        PortalOld portalOld = new PortalOld(name, startingDir);
        portals.put(name, portalOld);
    }

    public static PortalOld getPortal(String name) {
        PortalOld portalOld =  portals.get(name);
        portalOld.setPlugins(initialPlugins);
        return portalOld;
    }

//    @RequiresApi(api = Build.VERSION_CODES.N)
//    public static Fragment getFragment(String name) {
//        Portal portal = getPortal(name);
//        initialPlugins.forEach(portal::addPlugin);
//        return portal.getFragment();
//    }

}
