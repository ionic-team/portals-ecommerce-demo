package io.ionic.demo.ecommerce.plugins;

import androidx.lifecycle.ViewModel;

public class ShopAPIViewModel extends ViewModel {

    CheckoutCallback checkoutCallback;

    public void setCheckoutCallback(CheckoutCallback checkoutCallback) {
        this.checkoutCallback = checkoutCallback;
    }

    public void onCheckout(String result) {
        if(checkoutCallback != null) {
            this.checkoutCallback.checkout(result);
        }
    }
}

