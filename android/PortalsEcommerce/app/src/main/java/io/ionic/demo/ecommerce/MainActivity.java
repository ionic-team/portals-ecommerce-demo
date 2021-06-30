package io.ionic.demo.ecommerce;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.viewpager2.widget.ViewPager2;

/**
 * The parent Activity used for the E-Commerce app.
 */
public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    PageAdapter pageAdapter;
    ViewPager2 viewPager;

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
        viewPager.setPageTransformer(new FadePageTransformer());
        viewPager.setUserInputEnabled(false);
        viewPager.setOffscreenPageLimit(3);

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
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
                        setTitle("Store");
                        break;
                    case 1:
                        getSupportActionBar().show();
                        getSupportActionBar().setHomeButtonEnabled(false);
                        setTitle("Checkout");
                        break;
                    case 2:
                        getSupportActionBar().hide();
                        getSupportActionBar().setHomeButtonEnabled(false);
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

    public static class FadePageTransformer implements ViewPager2.PageTransformer {
        @Override
        public void transformPage(@NonNull View page, float position) {
            if(position <= -1.0F || position >= 1.0F) {
                page.setTranslationX(page.getWidth() * position);
                page.setAlpha(0.0F);
            } else if( position == 0.0F ) {
                page.setTranslationX(page.getWidth() * position);
                page.setAlpha(1.0F);
            } else {
                // position is between -1.0F & 0.0F OR 0.0F & 1.0F
                page.setTranslationX(page.getWidth() * -position);
                page.setAlpha(1.0F - Math.abs(position));
            }
        }
    }

//    @Override
//    public boolean onSupportNavigateUp() {
////        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
////        return NavigationUI.navigateUp(navController, appBarConfiguration) || super.onSupportNavigateUp();
//    }
}