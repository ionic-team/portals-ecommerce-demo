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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.util.Currency;

import io.ionic.demo.ecommerce.R;
import io.ionic.demo.ecommerce.data.ShoppingCart;
import io.ionic.demo.ecommerce.ui.store.ProductAdapter;
import io.ionic.demo.ecommerce.ui.store.StoreFragment;

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
        TextView subtotalTextView = root.findViewById(R.id.text_view_subtotal_value);
        TextView estimatedTotalTextView = root.findViewById(R.id.text_view_total_value);

        NumberFormat format = NumberFormat.getCurrencyInstance();
        format.setMaximumFractionDigits(2);
        format.setMinimumFractionDigits(2);
        format.setCurrency(Currency.getInstance("USD"));

        recyclerView = root.findViewById(R.id.cart_recycler_view);
        cartViewModel.getShoppingCart().observe(getViewLifecycleOwner(), products -> {
            cartAdapter = new CartAdapter(CartFragment.this.getContext());
            recyclerView.setLayoutManager(new LinearLayoutManager(CartFragment.this.getContext(), LinearLayoutManager.VERTICAL, false));
            recyclerView.setAdapter(cartAdapter);

            String price = format.format(cartViewModel.getShoppingCart().getValue().getTotalPriceOfProductsInCart());
            subtotalTextView.setText(price);
            estimatedTotalTextView.setText(price + " + Tax");
        });

        TextView shippingTextView = root.findViewById(R.id.text_view_shipping_value);
        shippingTextView.setText("Standard - Free");

        return root;
    }
}