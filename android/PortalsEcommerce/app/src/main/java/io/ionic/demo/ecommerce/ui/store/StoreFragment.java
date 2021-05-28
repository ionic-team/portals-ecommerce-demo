package io.ionic.demo.ecommerce.ui.store;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import io.ionic.demo.ecommerce.R;
import io.ionic.demo.ecommerce.data.model.Product;

public class StoreFragment extends Fragment {

    private StoreViewModel storeViewModel;

    private RecyclerView carouselView;
    private RecyclerView gridView;

    private ProductAdapter productAdapter;
    private ProductAdapter gridAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        storeViewModel = new ViewModelProvider(this).get(StoreViewModel.class);
        View root = inflater.inflate(R.layout.fragment_store, container, false);

        carouselView = root.findViewById(R.id.recycler_carousel);
        storeViewModel.getFeaturedProducts().observe(getViewLifecycleOwner(), new Observer<ArrayList<Product>>() {
            @Override
            public void onChanged(ArrayList<Product> products) {
                productAdapter = new ProductAdapter(StoreFragment.this.getContext(), products);
                carouselView.setLayoutManager(new LinearLayoutManager(StoreFragment.this.getContext(), LinearLayoutManager.HORIZONTAL, false));
                carouselView.setAdapter(productAdapter);
            }
        });

        gridView = root.findViewById(R.id.recycler_product_grid);
        storeViewModel.getAllProducts().observe(getViewLifecycleOwner(), products -> {
            gridAdapter = new ProductAdapter(StoreFragment.this.getContext(), products, true);
            gridView.setLayoutManager(new GridLayoutManager(StoreFragment.this.getContext(), 2));
            gridView.setAdapter(gridAdapter);
        });

        return root;
    }
}