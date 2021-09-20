package io.ionic.demo.ecommerce.portals;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

import com.getcapacitor.WebViewListener;
import io.ionic.portals.Portal;
import io.ionic.portals.PortalFragment;

public class FadePortalFragment extends PortalFragment {

    private View fadeView;

    private long duration = 500;
    private int colorResource = android.R.color.white;

    public static FadePortalFragment newInstance(String startDir) {
        return newInstance(startDir, android.R.color.white, 500);
    }

    public static FadePortalFragment newInstance(String startDir, int colorResource, long duration) {
        FadePortalFragment fragment = new FadePortalFragment();
        fragment.duration = duration;
        fragment.colorResource = colorResource;
        return fragment;
    }

    public FadePortalFragment() {
        super();
        addFadeListener();
    }

    public FadePortalFragment(Portal portal) {
        super(portal);
        addFadeListener();
    }

    private void addFadeListener() {
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

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(getBridge()!= null) {
            this.fadeView = new View(getActivity());
            fadeView.setId(View.generateViewId());

            fadeView.setLayoutParams(getBridge().getWebView().getLayoutParams());
            fadeView.setBackgroundResource(colorResource);
            getBridge().getWebView().addView(fadeView, 0);
            fadeView.bringToFront();
        }
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

}