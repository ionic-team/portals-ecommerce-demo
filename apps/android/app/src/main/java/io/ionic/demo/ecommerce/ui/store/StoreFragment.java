package io.ionic.demo.ecommerce.ui.store;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import io.ionic.demo.ecommerce.MainActivity;
import io.ionic.demo.ecommerce.R;
import io.ionic.demo.ecommerce.data.model.Product;

/**
 * Displays products to be purchased.
 */
public class StoreFragment extends Fragment {

    private MainActivity context;

    private StoreViewModel storeViewModel;

    private RecyclerView carouselView;
    private RecyclerView gridView;

    private ProductAdapter productAdapter;
    private ProductAdapter gridAdapter;

    public static StoreFragment newInstance(MainActivity context) {
        StoreFragment storeFragment = new StoreFragment();
        storeFragment.setContext(context);
        return storeFragment;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        storeViewModel = new ViewModelProvider(this).get(StoreViewModel.class);
        View root = inflater.inflate(R.layout.fragment_store, container, false);

        // A carousel view used to highlight best-selling items
        carouselView = root.findViewById(R.id.recycler_carousel);
        storeViewModel.getFeaturedProducts().observe(getViewLifecycleOwner(), new Observer<ArrayList<Product>>() {
            @Override
            public void onChanged(ArrayList<Product> products) {
                productAdapter = new ProductAdapter(context, products);
                productAdapter.setStateRestorationPolicy(RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY);
                carouselView.setLayoutManager(new LinearLayoutManager(StoreFragment.this.getContext(), LinearLayoutManager.HORIZONTAL, false));
                carouselView.setAdapter(productAdapter);
            }
        });

        // A grid view used to display all products available to purchase
        gridView = root.findViewById(R.id.recycler_product_grid);
        storeViewModel.getAllProducts().observe(getViewLifecycleOwner(), products -> {
            gridAdapter = new ProductAdapter(context, products, true);
            gridAdapter.setStateRestorationPolicy(RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY);
            gridView.setLayoutManager(new GridLayoutManager(StoreFragment.this.getContext(), 2));
            gridView.setAdapter(gridAdapter);
        });

        return root;
    }

    private void setContext(MainActivity context) {
        this.context = context;
    }
}