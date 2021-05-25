package io.ionic.demo.ecommerce;

import android.app.Application;
import android.content.Context;

public class EcommerceApp extends Application {

    private static EcommerceApp instance;

    public static EcommerceApp getInstance() {
        return instance;
    }

    public static Context getContext() {
        return instance.getApplicationContext();
    }

    @Override
    public void onCreate() {
        instance = this;
        super.onCreate();
    }
}
