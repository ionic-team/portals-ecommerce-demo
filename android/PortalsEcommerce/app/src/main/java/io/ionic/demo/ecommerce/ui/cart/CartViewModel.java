package io.ionic.demo.ecommerce.ui.cart;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import io.ionic.demo.ecommerce.EcommerceApp;
import io.ionic.demo.ecommerce.data.ShoppingCart;

/**
 * A view model for the shopping cart fragment.
 *
 * Used to retrieve the shopping cart data and display it to the user.
 */
public class CartViewModel extends ViewModel {

    /**
     * The shopping cart state.
     */
    final private MutableLiveData<ShoppingCart> shoppingCart;

    /**
     * Constructs a shopping cart view model.
     */
    public CartViewModel() {
        shoppingCart = new MutableLiveData<>();
        shoppingCart.setValue(EcommerceApp.getInstance().getShoppingCart());
    }

    /**
     * Gets the state of the shopping cart.
     *
     * @return The state of the shopping cart.
     */
    public MutableLiveData<ShoppingCart> getShoppingCart() {
        return shoppingCart;
    }
}