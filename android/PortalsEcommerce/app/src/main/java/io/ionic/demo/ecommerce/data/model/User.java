package io.ionic.demo.ecommerce.data.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import io.ionic.demo.ecommerce.R;

/**
 * An app user.
 */
public class User {
    public int id;
    public String firstName;
    public String lastName;
    public String email;
    public String image;
    public ArrayList<Address> addresses;
    public ArrayList<CreditCard> creditCards;

    public String getImageBase64(Context context) {
        if (image != null && !image.isEmpty()) {
            if (image.equals("user-image.jpg")) {
                File image = new File(context.getFilesDir(), "user-image.jpg");

                try (FileInputStream inputStream = new FileInputStream(image)) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        stringBuilder.append(line).append("\n");
                    }
                    reader.close();
                    return "data:image/jpeg;base64," + stringBuilder.toString();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                try (ByteArrayOutputStream byteStream = new ByteArrayOutputStream()) {
                    Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.jt_avatar);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteStream);
                    byte[] imageBytes = byteStream.toByteArray();
                    return "data:image/jpeg;base64," + Base64.encodeToString(imageBytes, Base64.DEFAULT);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }
}
