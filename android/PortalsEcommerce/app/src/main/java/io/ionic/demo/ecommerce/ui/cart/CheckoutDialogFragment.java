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
import androidx.lifecycle.ViewModelProvider;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

import io.ionic.demo.ecommerce.R;
import io.ionic.demo.ecommerce.plugins.CheckoutCallback;
import io.ionic.demo.ecommerce.plugins.ShopAPIViewModel;
import io.ionic.portals.Portal;
import io.ionic.portals.PortalFragment;
import io.ionic.portals.PortalManager;
import io.ionic.portals.PortalMethod;

/**
 * Displays an Ionic Portal containing a checkout app.
 */
public class CheckoutDialogFragment extends DialogFragment {
    Portal checkoutPortal;
    PortalFragment portalFragment;

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
        portalFragment = new PortalFragment(checkoutPortal);
        portalFragment.linkMessageReceivers(this);

        fragmentManager.beginTransaction().replace(R.id.checkout_web_app, portalFragment).commit();

    }

    @PortalMethod
    public void dismiss(String result) {
        if(result != null && (result.equals("cancel") || result.equals("success"))) {
            this.dismiss();
        }
    }
}
