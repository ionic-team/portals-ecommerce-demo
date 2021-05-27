package io.ionic.demo.ecommerce.ui.store;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import io.ionic.demo.ecommerce.data.DataReader;

public class StoreViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public StoreViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is the store view");
    }

    public LiveData<String> getText() {
        return mText;
    }
}