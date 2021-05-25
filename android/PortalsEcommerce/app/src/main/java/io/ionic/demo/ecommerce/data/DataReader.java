package io.ionic.demo.ecommerce.data;

import android.content.Context;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import io.ionic.demo.ecommerce.EcommerceApp;
import io.ionic.demo.ecommerce.data.model.AppData;

public class DataReader {

    private static DataReader dataReader = null;

    private AppData appData;

    private DataReader() {
        Context context = EcommerceApp.getContext();

        try {
            InputStream inputStream = context.getAssets().open("app.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();

            Gson gson = new Gson();
            appData = gson.fromJson(new String(buffer, StandardCharsets.UTF_8), AppData.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static DataReader getInstance() {
        if (dataReader == null) {
            dataReader = new DataReader();
        }

        return dataReader;
    }

    public AppData getAppData() {
        return appData;
    }

}
