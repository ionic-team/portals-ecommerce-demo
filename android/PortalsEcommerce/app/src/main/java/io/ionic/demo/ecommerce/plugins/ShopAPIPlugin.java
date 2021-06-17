package io.ionic.demo.ecommerce.plugins;

import androidx.lifecycle.ViewModelProvider;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import io.ionic.demo.ecommerce.EcommerceApp;
import io.ionic.demo.ecommerce.data.DataService;
import io.ionic.demo.ecommerce.data.ShoppingCart;
import io.ionic.demo.ecommerce.data.model.Cart;
import io.ionic.demo.ecommerce.data.model.User;

@CapacitorPlugin(name = "ShopAPI")
public class ShopAPIPlugin extends Plugin {

    DataService dataService;

    public ShopAPIPlugin() {
        dataService = DataService.getInstance();
    }

    @PluginMethod
    public void getCart(PluginCall call) {
        try {
            Cart cart = EcommerceApp.getInstance().getShoppingCart().getCart();
            String cartJson = new Gson().toJson(cart);
            JSObject cartJSObject = JSObject.fromJSONObject(new JSONObject(cartJson));
            call.resolve(cartJSObject);
        } catch (JSONException e) {
            call.reject("error decoding cart object");
        }
    }

    @PluginMethod
    public void getUserDetails(PluginCall call) {
        try {
            User user = dataService.getUser();
            String userJson = new Gson().toJson(user);
            JSObject userJSObject = JSObject.fromJSONObject(new JSONObject(userJson));
            call.resolve(userJSObject);
        } catch (JSONException e) {
            call.reject("error decoding user object");
        }
    }

    @PluginMethod
    public void updateUserDetails(PluginCall call) {
        JSObject userJSObject = call.getData();
        User user = new Gson().fromJson(userJSObject.toString(), User.class);
        dataService.setUser(user);
    }

    @PluginMethod
    public void checkoutResult(PluginCall call) {
        String result = call.getString("result");
        ShoppingCart cart = EcommerceApp.getInstance().getShoppingCart();
        cart.checkout(result);
        ShopAPIViewModel viewModel = new ViewModelProvider(this.getActivity()).get(ShopAPIViewModel.class);
        viewModel.onCheckout(result);
    }

    @PluginMethod
    public void getUserPicture(PluginCall call) {
        // todo
    }

    @PluginMethod
    public void setUserPicture(PluginCall call) {
        // todo
    }
}

