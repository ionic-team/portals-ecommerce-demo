package io.ionic.demo.ecommerce.ui.cart;

import android.app.ActionBar;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.getcapacitor.BridgeFragment;
import com.getcapacitor.WebViewListener;

import io.ionic.demo.ecommerce.R;

/**
 * Displays an Ionic Portal containing a checkout app.
 */
public class CheckoutDialogFragment extends DialogFragment {

    BridgeFragment embeddedFragment;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_checkout, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

        fragmentManager.beginTransaction().replace(R.id.checkout_web_app, embeddedFragment).commit();
    }
}
