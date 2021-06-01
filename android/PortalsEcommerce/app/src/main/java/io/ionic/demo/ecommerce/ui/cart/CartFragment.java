package io.ionic.demo.ecommerce.ui.cart;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import io.ionic.demo.ecommerce.R;
import io.ionic.demo.ecommerce.data.ShoppingCart;

/**
 * Displays a shopping cart.
 */
public class CartFragment extends Fragment {

    private CartViewModel cartViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);

        View root = inflater.inflate(R.layout.fragment_cart, container, false);
        final TextView textView = root.findViewById(R.id.text_dashboard);

        // Setup add to cart functionality
        final Button addToCartButton = root.findViewById(R.id.cart_checkout_button);
        addToCartButton.setOnClickListener(v -> {
            Navigation.findNavController(getView()).navigate(R.id.navigation_checkout);
        });

        cartViewModel.getShoppingCart().observe(getViewLifecycleOwner(), new Observer<ShoppingCart>() {
            @Override
            public void onChanged(ShoppingCart shoppingCart) {
                textView.setText(shoppingCart.toString());
            }
        });

        return root;
    }
}