package io.ionic.demo.ecommerce.ui.store;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import io.ionic.demo.ecommerce.data.DataReader;
import io.ionic.demo.ecommerce.data.model.Product;

public class StoreViewModel extends ViewModel {

    private final MutableLiveData<ArrayList<Product>> productList;

    public StoreViewModel() {
        productList = new MutableLiveData<>();

        // init data
        getProductList();
    }

    void getProductList() {
        ArrayList<Product> products = DataReader.getInstance().getAppData().products;
        productList.setValue(products);
    }

    public LiveData<ArrayList<Product>> getProducts() {
        return productList;
    }

    public LiveData<ArrayList<Product>> getAllProducts() {
        return productList;
    }

}