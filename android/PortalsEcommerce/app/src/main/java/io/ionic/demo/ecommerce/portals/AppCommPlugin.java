package io.ionic.demo.ecommerce.portals;

import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

@CapacitorPlugin(name = "AppCommunication")
public class AppCommPlugin extends Plugin {

    @PluginMethod
    public void getCart(PluginCall pluginCall) {
        // Stub for now
        pluginCall.resolve();
    }

    @PluginMethod
    public void getUserDetails(PluginCall pluginCall) {
        // Stub for now
        pluginCall.resolve();
    }

    @PluginMethod
    public void updateUserDetails(PluginCall pluginCall) {
        // Stub for now
        pluginCall.resolve();
    }

    @PluginMethod
    public void checkoutResult(PluginCall pluginCall) {
        // Stub for now
        pluginCall.resolve();
    }

    @PluginMethod
    public void getUserPicture(PluginCall pluginCall) {
        // Stub for now
        pluginCall.resolve();
    }

    @PluginMethod
    public void setUserPicture(PluginCall pluginCall) {
        // Stub for now
        pluginCall.resolve();
    }
}
