package io.ionic.demo.ecommerce.ui.profile;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

//import com.capacitorjs.plugins.camera.CameraPlugin;

import java.util.HashMap;

import io.ionic.demo.ecommerce.R;
import io.ionic.portalslibrary.Portal;
import io.ionic.portalslibrary.PortalFragment;
import io.ionic.portalslibrary.PortalManager;

public class ProfileFragment extends Fragment {
    PortalFragment portalFragment;
    Portal profilePortal;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        setHasOptionsMenu(false);

        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final FragmentManager fragmentManager = getParentFragmentManager();

        profilePortal = PortalManager.getPortal("webapp");

        profilePortal.setInitialContext("{ \"startingRoute\": \"/user\" }");
        portalFragment = new PortalFragment(profilePortal);

        // Inflate the fragment
        fragmentManager.beginTransaction().replace(R.id.profile_web_app, portalFragment).commit();
    }
}