package io.ionic.demo.ecommerce.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.capacitorjs.plugins.camera.CameraPlugin;
import com.getcapacitor.BridgeFragment;
import com.getcapacitor.WebViewListener;

import io.ionic.demo.ecommerce.R;
import io.ionic.demo.ecommerce.plugins.ShopAPIPlugin;
import io.ionic.demo.ecommerce.portals.FadeBridgeFragment;

public class ProfileFragment extends Fragment {

    BridgeFragment embeddedFragment;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final FragmentManager fragmentManager = getParentFragmentManager();

        embeddedFragment = FadeBridgeFragment.newInstance("webapp", android.R.color.white, 500);
        embeddedFragment.addWebViewListener(new WebViewListener() {
            private boolean isLoaded = false;
            @Override
            public void onPageLoaded(WebView webView) {
                super.onPageLoaded(webView);

                if (!isLoaded) {
                    isLoaded = true;
                    webView.evaluateJavascript("window.location.href = \"/user\"", null);
                }
            }
        });

        // Add plugins
        embeddedFragment.addPlugin(ShopAPIPlugin.class);
        embeddedFragment.addPlugin(CameraPlugin.class);

        // Inflate the fragment
        fragmentManager.beginTransaction().replace(R.id.profile_web_app, embeddedFragment).commit();
    }
}