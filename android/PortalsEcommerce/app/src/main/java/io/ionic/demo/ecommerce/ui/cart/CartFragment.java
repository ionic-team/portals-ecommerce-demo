package io.ionic.demo.ecommerce.ui.cart;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.BottomNavigationMenu;

import java.text.NumberFormat;
import java.util.Currency;

import io.ionic.demo.ecommerce.EcommerceApp;
import io.ionic.demo.ecommerce.R;
import io.ionic.demo.ecommerce.data.ShoppingCart;

/**
 * Displays a shopping cart.
 */
public class CartFragment extends Fragment {

    private CartAdapter cartAdapter;
    private CartViewModel cartViewModel;
    private RecyclerView recyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);
        View root = inflater.inflate(R.layout.fragment_cart, container, false);

        // Setup checkout to cart functionality
        final Button checkoutButton = root.findViewById(R.id.cart_checkout_button);
        checkoutButton.setOnClickListener(v -> {
            Navigation.findNavController(getView()).navigate(R.id.navigation_checkout);
        });

        // Observe values on cart
        recyclerView = root.findViewById(R.id.cart_recycler_view);
        cartViewModel.getShoppingCart().observe(getViewLifecycleOwner(), products -> {
            cartAdapter = new CartAdapter(CartFragment.this.getActivity(), v -> {
                updateCartView(root);
            });
            recyclerView.setLayoutManager(new LinearLayoutManager(CartFragment.this.getContext(), LinearLayoutManager.VERTICAL, false));
            recyclerView.setAdapter(cartAdapter);
            updateCartView(root);
        });

        return root;
    }

    private void updateCartView(View root) {
        TextView subtotalTextView = root.findViewById(R.id.text_view_subtotal_value);
        TextView estimatedTotalTextView = root.findViewById(R.id.text_view_total_value);
        TextView shippingTextView = root.findViewById(R.id.text_view_shipping_value);

        NumberFormat format = NumberFormat.getCurrencyInstance();
        format.setMaximumFractionDigits(2);
        format.setMinimumFractionDigits(2);
        format.setCurrency(Currency.getInstance("USD"));
        String price = format.format(cartViewModel.getShoppingCart().getValue().getTotalPriceOfProductsInCart());

        subtotalTextView.setText(price);
        estimatedTotalTextView.setText(price + " + Tax");
        shippingTextView.setText(R.string.standard_shipping);

        // Show/Hide if empty cart
        int itemCount = EcommerceApp.getInstance().getShoppingCart().getTotalItemCount();
        System.out.println(itemCount);

        if (itemCount == 0) {
            showEmptyCartViews(root);
        } else {
            showNonEmptyCartViews(root);
        }

    }

    private void showEmptyCartViews(View root) {
        // middle "cart-empty" text view
        root.findViewById(R.id.text_view_cart_empty).setVisibility(View.VISIBLE);

        // bottom text views
        root.findViewById(R.id.text_view_total_text).setVisibility(View.INVISIBLE);
        root.findViewById(R.id.text_view_total_value).setVisibility(View.INVISIBLE);
        root.findViewById(R.id.text_view_shipping_text).setVisibility(View.INVISIBLE);
        root.findViewById(R.id.text_view_shipping_value).setVisibility(View.INVISIBLE);
        root.findViewById(R.id.text_view_subtotal_text).setVisibility(View.INVISIBLE);
        root.findViewById(R.id.text_view_subtotal_value).setVisibility(View.INVISIBLE);

        // button
        root.findViewById(R.id.cart_checkout_button).setVisibility(View.INVISIBLE);
    }

    private void showNonEmptyCartViews(View root) {
        // middle "cart-empty" text view
        root.findViewById(R.id.text_view_cart_empty).setVisibility(View.INVISIBLE);

        // bottom text views
        root.findViewById(R.id.text_view_total_text).setVisibility(View.VISIBLE);
        root.findViewById(R.id.text_view_total_value).setVisibility(View.VISIBLE);
        root.findViewById(R.id.text_view_shipping_text).setVisibility(View.VISIBLE);
        root.findViewById(R.id.text_view_shipping_value).setVisibility(View.VISIBLE);
        root.findViewById(R.id.text_view_subtotal_text).setVisibility(View.VISIBLE);
        root.findViewById(R.id.text_view_subtotal_value).setVisibility(View.VISIBLE);

        // button
        root.findViewById(R.id.cart_checkout_button).setVisibility(View.VISIBLE);
    }
}