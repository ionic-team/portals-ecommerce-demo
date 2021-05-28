package io.ionic.demo.ecommerce.data.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Product implements Parcelable {
    public int id;
    public String title;
    public String description;
    public String image;
    public String category;
    public float price;

    protected Product(Parcel in) {
        id = in.readInt();
        title = in.readString();
        description = in.readString();
        image = in.readString();
        category = in.readString();
        price = in.readFloat();
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(image);
        dest.writeString(category);
        dest.writeFloat(price);
    }
}
