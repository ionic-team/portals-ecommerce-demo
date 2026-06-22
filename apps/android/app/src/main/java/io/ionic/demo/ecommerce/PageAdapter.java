package io.ionic.demo.ecommerce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import io.ionic.demo.ecommerce.ui.cart.CartFragment;
import io.ionic.demo.ecommerce.ui.profile.ProfileFragment;
import io.ionic.demo.ecommerce.ui.store.StoreContainerFragment;
import io.ionic.demo.ecommerce.ui.store.StoreFragment;

public class PageAdapter extends FragmentStateAdapter {

    private MainActivity context;

    public PageAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    public PageAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    public PageAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, MainActivity context) {
        super(fragmentManager, lifecycle);
        this.context = context;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment = null;
        switch(position) {
            case 0:
                fragment = StoreContainerFragment.newInstance(context);
                break;
            case 1:
                fragment = new CartFragment();
                break;
            case 2:
                fragment = ProfileFragment.newInstance();
                break;
        }

        return fragment;
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
