package io.ionic.demo.ecommerce.ui.profile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import io.ionic.demo.ecommerce.data.DataReader;

public class ProfileViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ProfileViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue(DataReader.getInstance().getAppData().profile.description);
    }

    public LiveData<String> getText() {
        return mText;
    }
}