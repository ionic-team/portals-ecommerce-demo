package io.ionic.demo.ecommerce.ui.cart;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.tabs.TabLayout;

import java.text.NumberFormat;
import java.util.Currency;

import io.ionic.demo.ecommerce.EcommerceApp;
import io.ionic.demo.ecommerce.R;

/**
 * Displays a shopping cart.
 */
public class CartFragment extends Fragment {

    private CartAdapter cartAdapter;
    private CartViewModel cartViewModel;
    private RecyclerView recyclerView;
    private View root;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        cartViewModel = new ViewModelProvider(requireActivity()).get(CartViewModel.class);
        root = inflater.inflate(R.layout.fragment_cart, container, false);

        // Setup checkout to cart functionality
        final Button checkoutButton = root.findViewById(R.id.cart_checkout_button);
        checkoutButton.setOnClickListener(v -> {
            FragmentManager fm = getParentFragmentManager();
            CheckoutDialogFragment fragment = new CheckoutDialogFragment();
            fragment.show(fm, "checkout");
        });

        // Observe values on cart
        recyclerView = root.findViewById(R.id.cart_recycler_view);
        cartViewModel.getShoppingCart().observe(getViewLifecycleOwner(), products -> {
            updateCartView(root);
        });

        return root;
    }

    @Override
    public void setMenuVisibility(boolean isVisible) {
        super.setMenuVisibility(isVisible);
        if (isVisible){
            updateCartView(root);
            setHasOptionsMenu(true);
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
    }

    private void updateCartView(View root) {
        cartAdapter = new CartAdapter(CartFragment.this.getActivity(), v -> updateCartView(root));
        recyclerView.setLayoutManager(new LinearLayoutManager(CartFragment.this.getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(cartAdapter);

        TextView subtotalTextView = root.findViewById(R.id.text_view_subtotal_value);
        TextView estimatedTotalTextView = root.findViewById(R.id.text_view_total_value);
        TextView shippingTextView = root.findViewById(R.id.text_view_shipping_value);

        NumberFormat format = NumberFormat.getCurrencyInstance();
        format.setMaximumFractionDigits(2);
        format.setMinimumFractionDigits(2);
        format.setCurrency(Currency.getInstance("USD"));
        String price = format.format(cartViewModel.getShoppingCart().getValue().getTotalPriceOfProductsInCart());

        subtotalTextView.setText(price);
        estimatedTotalTextView.setText(String.format("%s + Tax", price));
        shippingTextView.setText(R.string.standard_shipping);

        // Show/Hide if empty cart
        TabLayout navView = getActivity().findViewById(R.id.tab_layout);
        BadgeDrawable badge = navView.getTabAt(1).getOrCreateBadge();
        int itemCount = EcommerceApp.getInstance().getShoppingCart().getTotalItemCount();
        if (itemCount == 0) {
            showEmptyCartViews(root);
            badge.setVisible(false);
        } else {
            badge.setVisible(true);
            badge.setNumber(EcommerceApp.getInstance().getShoppingCart().getTotalItemCount());
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