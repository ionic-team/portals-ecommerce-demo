package io.ionic.demo.ecommerce.ui.cart;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.util.Map;

import io.ionic.demo.ecommerce.R;
import io.ionic.demo.ecommerce.data.ShoppingCart;
import io.ionic.demo.ecommerce.data.model.Product;

public class CartFragment extends Fragment {

    private CartViewModel cartViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);

        View root = inflater.inflate(R.layout.fragment_cart, container, false);
        final TextView textView = root.findViewById(R.id.text_dashboard);

        cartViewModel.getShoppingCart().observe(getViewLifecycleOwner(), new Observer<ShoppingCart>() {
            @Override
            public void onChanged(ShoppingCart shoppingCart) {
                textView.setText(shoppingCart.toString());
            }
        });

        return root;
    }
}