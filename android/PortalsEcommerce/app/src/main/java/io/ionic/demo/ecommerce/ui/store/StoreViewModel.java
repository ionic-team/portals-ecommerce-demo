package io.ionic.demo.ecommerce.ui.store;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Collections;

import io.ionic.demo.ecommerce.data.DataReader;
import io.ionic.demo.ecommerce.data.model.Product;

public class StoreViewModel extends ViewModel {

    private final MutableLiveData<ArrayList<Product>> productList;
    private final MutableLiveData<ArrayList<Product>> featuredProducts;

    public StoreViewModel() {
        productList = new MutableLiveData<>();
        featuredProducts = new MutableLiveData<>();

        // init data
        setupProductLists();
    }

    void setupProductLists() {
        ArrayList<Product> products = DataReader.getInstance().getAppData().products;

        // Featured products list
        ArrayList<Product> featured = new ArrayList<>();
        for (Product product : products) {
            if (product.category.equalsIgnoreCase("MustHaves")) {
                featured.add(product);
            }
        }

        featuredProducts.setValue(featured);

        // All products list
        Collections.shuffle(products);
        productList.setValue(products);
    }

    public LiveData<ArrayList<Product>> getFeaturedProducts() {
        return featuredProducts;
    }

    public LiveData<ArrayList<Product>> getAllProducts() {
        return productList;
    }

}