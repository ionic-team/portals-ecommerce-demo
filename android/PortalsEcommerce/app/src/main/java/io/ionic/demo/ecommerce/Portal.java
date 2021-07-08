package io.ionic.demo.ecommerce;

import android.os.Build;
import android.webkit.WebView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.getcapacitor.BridgeFragment;
import com.getcapacitor.Plugin;
import com.getcapacitor.WebViewListener;

import java.util.ArrayList;
import java.util.List;

public class Portal {
//    public BridgeFragment fragment;
    public String startingContext;

    private List<Class<? extends Plugin>> initialPlugins = new ArrayList<>();
    private String startDir;

    public Portal(String startDir) {
       this.startDir = startDir;
    }

    public void addPlugin(Class<? extends Plugin> plugin) {
        this.initialPlugins.add(plugin);
    }

    public void setPlugins(List<Class<? extends Plugin>> plugins) {
        this.initialPlugins = plugins;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public Fragment getFragment() {
        BridgeFragment fragment = BridgeFragment.newInstance(startDir);
        this.initialPlugins.forEach(fragment::addPlugin);
        //todo: find better way to inject initial context into web view
        fragment.addWebViewListener(new WebViewListener() {
            @Override
            public void onPageLoaded(WebView webView) {
                webView.evaluateJavascript("window.portalInitialContext = " + startingContext, null);
            }
        });
        return fragment;
    }

}
