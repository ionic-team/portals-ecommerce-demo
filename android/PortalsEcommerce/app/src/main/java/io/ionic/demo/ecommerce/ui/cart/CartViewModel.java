package io.ionic.demo.ecommerce.ui.cart;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import io.ionic.demo.ecommerce.data.DataReader;

public class CartViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public CartViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue(DataReader.getInstance().getAppData().cart.description);
    }

    public LiveData<String> getText() {
        return mText;
    }
}