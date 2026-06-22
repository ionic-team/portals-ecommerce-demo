package io.ionic.demo.ecommerce.data;

import android.content.Context;

import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import io.ionic.demo.ecommerce.EcommerceApp;
import io.ionic.demo.ecommerce.data.model.AppData;
import io.ionic.demo.ecommerce.data.model.Product;
import io.ionic.demo.ecommerce.data.model.User;

/**
 * Reads data for the e-commerce app from a JSON file in the app assets.
 */
public class DataService {

    /**
     * A single instance of this class.
     */
    private static DataService dataReader = null;

    /**
     * A plain Java object representation of the JSON data.
     */
    private AppData appData;

    /**
     * Constructs a DataReader object and reads the JSON file into memory.
     */
    private DataService(Context context) {
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

    public DataService(AppData appData) {
        this.appData = appData;
    }

    /**
     * Gets a singleton instance of the DataReader containing the loaded app data.
     *
     * @return A singleton instance of DataReader.
     */
    public static DataService getInstance(Context context) {
        if (dataReader == null) {
            dataReader = new DataService(context);
        }

        return dataReader;
    }

    /**
     * Gets the products.
     *
     * @return ArrayList of products.
     */
    public ArrayList<Product> getProducts() {
        return appData.products;
    }

    public User getUser() {
        return appData.user;
    }

    public void setUser(User user) {
        this.appData.user = user;
    }

    public boolean storeUserImage(Context context, User user, String image) {
        FileOutputStream saveStream;
        if (image != null) {
            try {
                saveStream = context.openFileOutput("user-image.jpg", Context.MODE_PRIVATE);
                saveStream.write(image.getBytes());
                saveStream.flush();
                saveStream.close();

                // image was saved
                user.image = "user-image.jpg";
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // image was not saved
        return false;
    }
}
