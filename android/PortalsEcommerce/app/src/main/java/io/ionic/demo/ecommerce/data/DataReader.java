package io.ionic.demo.ecommerce.data;

import android.content.Context;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import io.ionic.demo.ecommerce.EcommerceApp;
import io.ionic.demo.ecommerce.data.model.AppData;

/**
 * Reads data for the e-commerce app from a JSON file in the app assets.
 */
public class DataReader {

    /**
     * A single instance of this class.
     */
    private static DataReader dataReader = null;

    /**
     * A plain Java object representation of the JSON data.
     */
    private AppData appData;

    /**
     * Constructs a DataReader object and reads the JSON file into memory.
     */
    private DataReader() {
        Context context = EcommerceApp.getContext();

        try {
            InputStream inputStream = context.getAssets().open("data.json");
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

    /**
     * Gets a singleton instance of the DataReader containing the loaded app data.
     *
     * @return A singleton instance of DataReader.
     */
    public static DataReader getInstance() {
        if (dataReader == null) {
            dataReader = new DataReader();
        }

        return dataReader;
    }

    /**
     * Gets the app data.
     *
     * @return The plain Java object representation of the app data.
     */
    public AppData getAppData() {
        return appData;
    }

}
