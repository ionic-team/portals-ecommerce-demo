package io.ionic.demo.ecommerce;

import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

//import com.capacitorjs.plugins.camera.CameraPlugin;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import io.ionic.demo.ecommerce.data.model.Product;
import io.ionic.demo.ecommerce.plugins.ShopAPIPlugin;
import io.ionic.demo.ecommerce.ui.product.HelpFragment;

/**
 * The parent Activity used for the E-Commerce app.
 */
public class MainActivity extends AppCompatActivity {

    TabLayout tabLayout;
    PageAdapter pageAdapter;
    ViewPager2 viewPager;

    Product selectedProduct;
    boolean hideMenu = true;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Store");

        pageAdapter = new PageAdapter(getSupportFragmentManager(), getLifecycle(), this);
        viewPager = findViewById(R.id.pager);
        viewPager.setAdapter(pageAdapter);
        viewPager.setUserInputEnabled(false);
        viewPager.setOffscreenPageLimit(3);

        tabLayout = findViewById(R.id.tab_layout);
        new TabLayoutMediator(tabLayout, viewPager, true, false, (tab, position) -> {
            switch(position) {
                case 0:
                    tab.setIcon(R.drawable.ic_shop__material);
                    break;
                case 1:
                    tab.setIcon(R.drawable.ic_cart_material);
                    break;
                case 2:
                    tab.setIcon(R.drawable.ic_profile_material);
                    break;
            }
        }).attach();

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch(tab.getPosition()) {
                    case 0:
                        getSupportActionBar().show();
                        if (getSupportFragmentManager().getBackStackEntryCount() == 2) {
                            setTitle("Help");
                            showHelpMenu(false);
                            showUpButton();
                        } else if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
                            setTitle(selectedProduct.title);
                            showHelpMenu(true);
                            showUpButton();
                        }else {
                            setTitle("Store");
                            showHelpMenu(false);
                            hideUpButton();
                        }
                        break;
                    case 1:
                        getSupportActionBar().show();
                        hideUpButton();
                        showHelpMenu(false);
                        setTitle("Checkout");
                        break;
                    case 2:
                        getSupportActionBar().hide();
                        hideUpButton();
                        showHelpMenu(false);
                        setTitle("Profile");
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // not used
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // not used
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0 && tabLayout.getSelectedTabPosition() == 0) {
            super.onBackPressed();

            if(getSupportFragmentManager().getBackStackEntryCount() == 0) {
                setTitle("Store");
                selectedProduct = null;
                hideUpButton();
                showHelpMenu(false);
            } else if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
                showHelpMenu(true);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.help_menu, menu);

        if (hideMenu) {
            menu.getItem(0).setVisible(false);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        } else if (item.getItemId() == R.id.help) {
            Fragment helpFragment = new HelpFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.addToBackStack(null);
            transaction.replace(R.id.store_container_layout, helpFragment).commit();
            setTitle("Help");
            showHelpMenu(false);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setSelectedProduct(Product selectedProduct) {
        this.selectedProduct = selectedProduct;
        setTitle(selectedProduct.title);
    }

    private void hideUpButton() {
        getSupportActionBar().setHomeButtonEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    private void showUpButton() {
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void showHelpMenu(boolean showMenu) {
        hideMenu = !showMenu;
        invalidateOptionsMenu();
    }
}