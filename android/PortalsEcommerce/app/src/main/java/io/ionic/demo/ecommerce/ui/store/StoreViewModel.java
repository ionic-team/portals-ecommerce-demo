package io.ionic.demo.ecommerce.ui.store;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Collections;

import io.ionic.demo.ecommerce.EcommerceApp;
import io.ionic.demo.ecommerce.data.DataService;
import io.ionic.demo.ecommerce.data.model.Product;

/**
 * A view model for the products fragment.
 *
 * Used to retrieve products from the app data to be displayed in {@link StoreFragment}
 */
public class StoreViewModel extends ViewModel {

    /**
     * The product list for all products.
     */
    private final MutableLiveData<ArrayList<Product>> productList;

    /**
     * The product list for featured products.
     */
    private final MutableLiveData<ArrayList<Product>> featuredProducts;

    /**
     * Constructs a store view model.
     */
    public StoreViewModel() {
        productList = new MutableLiveData<>();
        featuredProducts = new MutableLiveData<>();

        // initialize data
        setupProductLists();
    }

    /**
     * Retrieves the product data and loads it into lists based on if it is featured
     * product data or not.
     */
    private void setupProductLists() {
        ArrayList<Product> products = DataService.getInstance(EcommerceApp.getContext()).getProducts();

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

    /**
     * Gets featured products for the Store.
     *
     * @return A list of featured products.
     */
    public LiveData<ArrayList<Product>> getFeaturedProducts() {
        return featuredProducts;
    }

    /**
     * Gets all products for the Store.
     *
     * @return A list of all products.
     */
    public LiveData<ArrayList<Product>> getAllProducts() {
        return productList;
    }

}