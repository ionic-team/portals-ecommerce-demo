package io.ionic.demo.ecommerce.ui.cart;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.getcapacitor.BridgeFragment;
import com.getcapacitor.WebViewListener;

import org.jetbrains.annotations.NotNull;

import io.ionic.demo.ecommerce.R;
import io.ionic.demo.ecommerce.plugins.CheckoutCallback;
import io.ionic.demo.ecommerce.plugins.ShopAPIPlugin;
import io.ionic.demo.ecommerce.plugins.ShopAPIViewModel;

/**
 * Displays an Ionic Portal containing a checkout app.
 */

public class CheckoutDialogFragment extends DialogFragment implements CheckoutCallback {
    BridgeFragment embeddedFragment;

    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_checkout, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.CartDialog);

        ShopAPIViewModel viewModel = new ViewModelProvider(requireActivity()).get(ShopAPIViewModel.class);
        viewModel.setCheckoutCallback(this);

        final FragmentManager fragmentManager = getChildFragmentManager();
        embeddedFragment = BridgeFragment.newInstance("webapp");
        embeddedFragment.addWebViewListener(new WebViewListener() {
            private boolean isLoaded = false;
            @Override
            public void onPageLoaded(WebView webView) {

                if (!isLoaded) {
                    isLoaded = true;
                    webView.evaluateJavascript("window.location.href = \"/checkout\"", null);
                }
            }
        });

        // Add plugins
        embeddedFragment.addPlugin(ShopAPIPlugin.class);

        fragmentManager.beginTransaction().replace(R.id.checkout_web_app, embeddedFragment).commit();

    }

    @Override
    public void checkout(String result) {
        if(result.equals("cancel") || result.equals("success")) {
            this.dismiss();
        }
    }
}
