package io.ionic.demo.ecommerce.ui.cart;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.getcapacitor.JSObject;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import io.ionic.demo.ecommerce.R;
import io.ionic.demo.ecommerce.plugins.ShopAPIPlugin;
import io.ionic.demo.ecommerce.portals.FadePortalFragment;
import io.ionic.portals.Portal;
import io.ionic.portals.PortalBuilder;
import io.ionic.portals.PortalManager;
import io.ionic.portals.PortalMethod;
import io.ionic.portals.PortalsPlugin;
import io.ionic.portals.PortalsPubSub;
import kotlin.Unit;

/**
 * Displays an Ionic Portal containing a checkout app.
 */
public class CheckoutDialogFragment extends DialogFragment {
    FadePortalFragment portalFragment;

//    PortalsPubSub pubSub = PortalsPubSub.getShared();
    private final PortalsPubSub pubSub = new PortalsPubSub();
    private final PortalsPlugin portalsPlugin = new PortalsPlugin(pubSub);

    private final Portal checkoutPortal = new PortalBuilder("pubsub")
            .setPortalFragmentType(FadePortalFragment.class)
            .setInitialContext(Map.of("startingRoute", "/pubsub"))
            .setStartDir("webapp")
            .addPlugin(ShopAPIPlugin.class)
            .addPluginInstance(portalsPlugin)
            .create();

    int spyRef;

    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_checkout, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.CartDialog);

        final FragmentManager fragmentManager = getChildFragmentManager();
        portalFragment = new FadePortalFragment(checkoutPortal);
        portalFragment.linkMessageReceivers(this, pubSub);
        portalFragment.setDuration(1200);

        spyRef = PortalsPubSub.getShared().subscribe("dismiss", ( result -> {
            Log.i("PUBSUB RECEIVED", "I'm a little spy listening on your events");
            return Unit.INSTANCE;
        }));

        fragmentManager.beginTransaction().replace(R.id.checkout_web_app, portalFragment).commit();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        PortalsPubSub.getShared().unsubscribe("dismiss", spyRef);
    }

    @PortalMethod
    public void sayHi(JSONObject data) {
        Log.i("PUBSUB RECEIVED", data.toString());
        try {
            String message = data.getString("message");
            String[] parts = message.split(":");
            Log.i("PUBSUB RECEIVED", Arrays.toString(parts));
            if (parts[0].equals("dismiss")) {
                pubSub.publish("dismiss", parts[1]);
            }

            if (parts[0].equals("echo")) {
                pubSub.publish("pubsub", parts[1]);
            }
        } catch (JSONException ignored) {

        }
    }

    @PortalMethod
    public void dismiss(String result) {
        if(result != null && (result.equals("cancel") || result.equals("success"))) {
            this.dismiss();
        }
    }
}
