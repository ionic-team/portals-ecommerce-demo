package io.ionic.demo.ecommerce.ui.product;

import android.os.Bundle;
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
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import java.text.NumberFormat;
import java.util.Currency;

import io.ionic.demo.ecommerce.EcommerceApp;
import io.ionic.demo.ecommerce.R;
import io.ionic.demo.ecommerce.data.model.Product;

/**
 * Displays information about a selected product.
 */
public class ProductFragment extends Fragment {

    private AppCompatActivity context;
    private Product product;

    public static ProductFragment newInstance(AppCompatActivity context, Product product) {
        ProductFragment productFragment = new ProductFragment();
        productFragment.setProduct(product);
        productFragment.setContext(context);
        return productFragment;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_product, container, false);

        setHasOptionsMenu(true);

        // Load product image
        final ImageView productImage = root.findViewById(R.id.product_image);
        String imageResourceName = product.image.substring(0, product.image.lastIndexOf(".")).replaceAll("-", "_");
        final int resourceId = getResources().getIdentifier(imageResourceName, "drawable", getContext().getPackageName());
        productImage.setImageResource(resourceId);

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
            TabLayout tabs = getActivity().findViewById(R.id.tab_layout);
            BadgeDrawable badge = tabs.getTabAt(1).getOrCreateBadge();
            badge.setVisible(true);
            badge.setNumber(EcommerceApp.getInstance().getShoppingCart().getTotalItemCount());
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
            // Navigate to product page passing the product to be displayed, when tapped
            Fragment helpFragment = new HelpFragment();
            FragmentTransaction transaction = context.getSupportFragmentManager().beginTransaction();
            transaction.addToBackStack(null);
            transaction.replace(R.id.product_layout, helpFragment).commit();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setProduct(Product product) {
        this.product = product;
    }

    private void setContext(AppCompatActivity context) {
        this.context = context;
    }
}
