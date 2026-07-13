package io.ionic.demo.ecommerce.ui.store;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import io.ionic.demo.ecommerce.MainActivity;
import io.ionic.demo.ecommerce.R;

/**
 * Displays products to be purchased.
 */
public class StoreContainerFragment extends Fragment {

    private MainActivity context;

    public static StoreContainerFragment newInstance(MainActivity context) {
        StoreContainerFragment storeFragment = new StoreContainerFragment();
        storeFragment.setContext(context);
        return storeFragment;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_store_container, container, false);

        StoreFragment storeFragment = StoreFragment.newInstance(context);
        FragmentTransaction transaction = context.getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.store_container_layout, storeFragment).commit();

        return root;
    }

    private void setContext(MainActivity context) {
        this.context = context;
    }
}
