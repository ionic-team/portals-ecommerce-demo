package io.ionic.demo.ecommerce.ui.profile;
import android.os.Bundle;
import android.view.View;

import org.jetbrains.annotations.NotNull;
import io.ionic.portals.Portal;
import io.ionic.portals.PortalFragment;
import io.ionic.portals.PortalManager;

public class ProfileFragment extends PortalFragment {

    public static ProfileFragment newInstance() {
        return new ProfileFragment(PortalManager.getPortal("profile"));
    }

    public ProfileFragment() {
        super();
    }

    public ProfileFragment(Portal portal) {
        super(portal);
    }

    @Override
    public void onViewCreated(@NotNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(false);
    }
}