package io.ionic.demo.ecommerce;

import android.os.Build;
import android.util.Log;
import android.webkit.WebView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.getcapacitor.BridgeFragment;
import com.getcapacitor.Plugin;
import com.getcapacitor.WebViewListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class PortalOld {
//    public BridgeFragment fragment;
    public Object startingContext;

    private List<Class<? extends Plugin>> initialPlugins = new ArrayList<>();
    private String name;
    private String startDir;

    public PortalOld(String name, String startDir) {
        this.name = name;
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
            public void onPageStarted(WebView webView) {
                if(startingContext != null) {
                    Gson gson = new Gson();
                    String jsonValue = gson.toJson(startingContext);
                    String portalInitialContext = "{ \"name\": \"" + name + "\"," +
                            " \"value\": " + jsonValue  +
                            " } ";
                    webView.evaluateJavascript("window.portalInitialContext = " + portalInitialContext
                            , null);
                }
            }
        });
        return fragment;
    }

}
