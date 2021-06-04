package io.ionic.demo.ecommerce.ui.product;

import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.Currency;

import io.ionic.demo.ecommerce.EcommerceApp;
import io.ionic.demo.ecommerce.R;
import io.ionic.demo.ecommerce.data.model.Product;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/**
 * Displays information about a selected product.
 */
public class ProductFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_product, container, false);

        setHasOptionsMenu(true);

        // Retrieve product data from navigation
        Product product = ProductFragmentArgs.fromBundle(getArguments()).getProduct();

        // Load product image
        final ImageView productImage = root.findViewById(R.id.product_image);
        String imageResourceName = product.image.substring(0, product.image.lastIndexOf("."));
        final int resourceId = getResources().getIdentifier(imageResourceName, "drawable", getContext().getPackageName());
        Picasso.get().load(resourceId).into(productImage);

        // Load product text data
        final TextView productTitle = root.findViewById(R.id.product_page_title);
        productTitle.setText(product.title);

        final TextView productPrice = root.findViewById(R.id.product_page_price);
        NumberFormat format = NumberFormat.getCurrencyInstance();
        format.setMaximumFractionDigits(0);
        format.setCurrency(Currency.getInstance("USD"));
        productPrice.setText(format.format(product.price));

        final TextView productDescription = root.findViewById(R.id.product_page_description);
        productDescription.setText(product.description);

        // Setup add to cart functionality
        final Button addToCartButton = root.findViewById(R.id.add_cart_button);
        addToCartButton.setOnClickListener(v -> {
            EcommerceApp.getInstance().getShoppingCart().addItem(product);

            // Increment badge number on the bottom nav
            BottomNavigationView navView = getActivity().findViewById(R.id.nav_view);
            BadgeDrawable badge = navView.getOrCreateBadge(R.id.navigation_cart);
            badge.setVisible(true);
            badge.setNumber(badge.getNumber()+1);
        });

        return root;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.help_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.help) {
            Navigation.findNavController(getView()).navigate(R.id.navigation_help);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
