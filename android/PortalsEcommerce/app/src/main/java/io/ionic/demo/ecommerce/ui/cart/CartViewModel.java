package io.ionic.demo.ecommerce.ui.cart;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import io.ionic.demo.ecommerce.EcommerceApp;
import io.ionic.demo.ecommerce.data.ShoppingCart;

public class CartViewModel extends ViewModel {

    final private MutableLiveData<ShoppingCart> shoppingCart;

    public CartViewModel() {
        shoppingCart = new MutableLiveData<>();
        shoppingCart.setValue(EcommerceApp.getInstance().getShoppingCart());
    }

    public LiveData<ShoppingCart> getShoppingCart() {
        return shoppingCart;
    }
}