package com.atharok.btremote.presentation.ui

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.atharok.btremote.R
import com.atharok.btremote.common.utils.bluetoothConnectPermissions
import com.atharok.btremote.databinding.FragmentBluetoothPermissionsBinding

class BluetoothPermissionsFragment : Fragment() {

    private var _binding: FragmentBluetoothPermissionsBinding? = null
    private val binding get() = _binding!!

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        if (permissions.values.all { it }) {
            findNavController().navigate(R.id.bluetoothActivationFragment)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentBluetoothPermissionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnAuthorize.setOnClickListener {
            val ungranted = bluetoothConnectPermissions.filter {
                ContextCompat.checkSelfPermission(requireContext(), it) != PackageManager.PERMISSION_GRANTED
            }
            if (ungranted.isEmpty()) {
                findNavController().navigate(R.id.bluetoothActivationFragment)
            } else {
                requestPermissionLauncher.launch(ungranted.toTypedArray())
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}