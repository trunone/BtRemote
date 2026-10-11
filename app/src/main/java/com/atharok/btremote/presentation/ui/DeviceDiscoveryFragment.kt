package com.atharok.btremote.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.atharok.btremote.R
import com.atharok.btremote.databinding.FragmentDeviceDiscoveryBinding
import com.atharok.btremote.presentation.services.BluetoothHidService
import com.atharok.btremote.presentation.viewmodel.DeviceDiscoveryViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class DeviceDiscoveryFragment : Fragment() {

    private var _binding: FragmentDeviceDiscoveryBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DeviceDiscoveryViewModel by viewModel()
    private lateinit var adapter: DeviceAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentDeviceDiscoveryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        adapter = DeviceAdapter(
            onDeviceClick = { device ->
                BluetoothHidService.start(requireContext())
                viewModel.connectDevice(device.address)
                findNavController().navigate(R.id.remoteFragment)
            },
            onUnpairClick = {}
        )

        binding.rvDiscoveredDevices.layoutManager = LinearLayoutManager(requireContext())
        binding.rvDiscoveredDevices.adapter = adapter

        binding.btnRefresh.setOnClickListener {
            viewModel.startDiscovery()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.scannedDevicesFlow.collectLatest { devices ->
                adapter.submitList(devices)
                if (devices.isEmpty()) {
                    binding.rvDiscoveredDevices.visibility = View.GONE
                    binding.tvEmpty.visibility = View.VISIBLE
                } else {
                    binding.rvDiscoveredDevices.visibility = View.VISIBLE
                    binding.tvEmpty.visibility = View.GONE
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.isDiscoveringFlow.collectLatest { isDiscovering ->
                binding.progressBar.visibility = if (isDiscovering) View.VISIBLE else View.GONE
            }
        }

        viewModel.registerBluetoothScannerReceiver()
        viewModel.startDiscovery()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.unregisterBluetoothScannerReceiver()
        _binding = null
    }
}