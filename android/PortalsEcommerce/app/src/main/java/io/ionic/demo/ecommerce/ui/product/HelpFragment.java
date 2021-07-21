package io.ionic.demo.ecommerce.ui.product;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.getcapacitor.WebViewListener;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

import io.ionic.demo.ecommerce.R;
import io.ionic.demo.ecommerce.plugins.ShopAPIPlugin;
import io.ionic.demo.ecommerce.portals.FadeBridgeFragment;
import io.ionic.portalslibrary.Portal;
import io.ionic.portalslibrary.PortalFragment;
import io.ionic.portalslibrary.PortalManager;
import io.ionic.portalslibrary.PortalView;

/**
 * Displays an Ionic Portal containing a help app.
 */
public class HelpFragment extends Fragment {

//    PortalFragment portalFragment;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_help, container, false);

    }

//    @Override
//    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
////        PortalView portalView = view.findViewById(R.id.help_web_app);
////        HashMap<String, String> initialContext = new HashMap<>();
////        initialContext.put("startingRoute", "/help");
////        Portal portal = PortalManager.getPortal("help");
////        portal.setInitialContext(initialContext);
////        portalFragment = portalView.getPortalFragment();
////        portalFragment.addWebViewListener(new WebViewListener() {
////            private boolean isLoaded = false;
////            @Override
////            public void onPageLoaded(WebView webView) {
////                super.onPageLoaded(webView);
////
////                if (!isLoaded) {
////                    isLoaded = true;
////                    webView.evaluateJavascript("window.portalInitialContext = {\"name\": \"help\", \"value\": { \"startingRoute\": \"/help\" }} ", null);
////                }
////            }
////        });
//    }

//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        final FragmentManager fragmentManager = getParentFragmentManager();
//
//        portalFragment = // FadeBridgeFragment.newInstance("webapp", android.R.color.white, 500);
//
//
//        // Add plugins
//        portalFragment.addPlugin(ShopAPIPlugin.class);
//
//        // Inflate the fragment
//        fragmentManager.beginTransaction().replace(R.id.help_web_app, portalFragment).commit();
//    }
}
