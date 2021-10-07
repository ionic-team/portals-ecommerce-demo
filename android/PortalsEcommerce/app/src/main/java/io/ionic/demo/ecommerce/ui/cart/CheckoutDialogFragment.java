package io.ionic.demo.ecommerce.ui.cart;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

import io.ionic.demo.ecommerce.R;
import io.ionic.demo.ecommerce.portals.FadePortalFragment;
import io.ionic.portals.Portal;
import io.ionic.portals.PortalManager;
import io.ionic.portals.PortalsPlugin;
import kotlin.Unit;

/**
 * Displays an Ionic Portal containing a checkout app.
 */
public class CheckoutDialogFragment extends DialogFragment {
    Portal checkoutPortal;
    FadePortalFragment portalFragment;

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
        checkoutPortal = PortalManager.getPortal("checkout");
        HashMap<String, String> initialContext = new HashMap<>();
        initialContext.put("startingRoute", "/checkout");
        checkoutPortal.setInitialContext(initialContext);
        portalFragment = new FadePortalFragment(checkoutPortal);
        portalFragment.setDuration(1200);

        // Subscribe to the dismiss topic to close the cart modal
        PortalsPlugin.subscribe("dismiss", subscriptionResult -> {
            dismiss((String) subscriptionResult.getData());
            return Unit.INSTANCE;
        });

        fragmentManager.beginTransaction().replace(R.id.checkout_web_app, portalFragment).commit();
    }

    public void dismiss(String result) {
        if(result != null && (result.equals("cancel") || result.equals("success"))) {
            this.dismiss();
        }
    }
}
