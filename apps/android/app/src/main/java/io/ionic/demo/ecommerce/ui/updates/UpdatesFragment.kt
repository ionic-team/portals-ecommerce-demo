package io.ionic.demo.ecommerce.ui.updates

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.android.material.button.MaterialButton
import io.ionic.demo.ecommerce.R
import io.ionic.demo.ecommerce.ui.profile.ProfileFragment
import io.ionic.portals.PortalManager
import kotlinx.coroutines.launch

class UpdatesFragment : Fragment() {

    private lateinit var statusText: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_updates, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        statusText = view.findViewById(R.id.update_status)
        val syncSuspendButton: MaterialButton = view.findViewById(R.id.sync_provider_suspend_button)
        val syncAsyncButton: MaterialButton = view.findViewById(R.id.sync_provider_async_button)
        val reloadButton: MaterialButton = view.findViewById(R.id.reload_provider_button)

        syncSuspendButton.setOnClickListener { syncProviderAppSuspend() }
        syncAsyncButton.setOnClickListener { syncProviderAppAsync() }
        reloadButton.setOnClickListener { reloadProviderApp() }
    }

    /** Demonstrates [io.ionic.portals.Portal.syncProvider], the Kotlin suspend-function API. */
    private fun syncProviderAppSuspend() {
        statusText.setText(R.string.update_status_syncing)
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                PortalManager.getPortal("profile").syncProvider()
                statusText.setText(R.string.update_status_synced)
            } catch (error: Exception) {
                statusText.text = getString(R.string.update_status_failed, error.message)
            }
        }
    }

    /** Demonstrates [io.ionic.portals.Portal.syncProviderAsync], the Java-friendly CompletableFuture API. */
    private fun syncProviderAppAsync() {
        statusText.setText(R.string.update_status_syncing)
        val mainExecutor = ContextCompat.getMainExecutor(requireContext())
        PortalManager.getPortal("profile").syncProviderAsync().whenCompleteAsync({ _, error ->
            if (error != null) {
                statusText.text = getString(R.string.update_status_failed, error.message)
            } else {
                statusText.setText(R.string.update_status_synced)
            }
        }, mainExecutor)
    }

    private fun reloadProviderApp() {
        statusText.setText(R.string.update_status_reloading)
        parentFragmentManager.setFragmentResult(ProfileFragment.RELOAD_REQUEST_KEY, Bundle())
    }
}
