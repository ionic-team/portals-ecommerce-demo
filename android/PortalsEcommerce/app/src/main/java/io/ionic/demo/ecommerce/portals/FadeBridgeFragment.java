package io.ionic.demo.ecommerce.portals;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.getcapacitor.BridgeFragment;
import com.getcapacitor.WebViewListener;

public class FadeBridgeFragment extends BridgeFragment {

    private static final String ARG_START_DIR = "startDir";

    private View fadeView;
    private ViewGroup container;

    private long duration;
    private int colorResource;

    public static FadeBridgeFragment newInstance(String startDir) {
        return newInstance(startDir, android.R.color.darker_gray, 500);
    }

    public static FadeBridgeFragment newInstance(String startDir, int colorResource, long duration) {
        FadeBridgeFragment fragment = new FadeBridgeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_START_DIR, startDir);
        fragment.setArguments(args);
        fragment.duration = duration;
        fragment.colorResource = colorResource;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.container = container;
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        this.fadeView = new View(getActivity());
        fadeView.setId(View.generateViewId());

        fadeView.setLayoutParams(bridge.getWebView().getLayoutParams());
        fadeView.setBackgroundResource(colorResource);
        container.addView(fadeView, 0);
        fadeView.bringToFront();
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public int getColorResource() {
        return colorResource;
    }

    public void setColorResource(int colorResource) {
        this.colorResource = colorResource;
        fadeView.setBackgroundResource(this.colorResource);
    }

    public FadeBridgeFragment() {
        super();

        addWebViewListener(new WebViewListener() {
            @Override
            public void onPageLoaded(WebView webView) {
                fadeView.animate()
                        .alpha(0f)
                        .setDuration(duration)
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                fadeView.setVisibility(View.GONE);
                            }
                        });
            }});
    }
}
