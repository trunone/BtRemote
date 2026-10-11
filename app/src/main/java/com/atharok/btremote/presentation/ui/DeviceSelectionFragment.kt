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
import com.atharok.btremote.databinding.FragmentDeviceSelectionBinding
import com.atharok.btremote.presentation.services.BluetoothHidService
import com.atharok.btremote.presentation.viewmodel.DeviceSelectionViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class DeviceSelectionFragment : Fragment() {

    private var _binding: FragmentDeviceSelectionBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DeviceSelectionViewModel by viewModel()
    private lateinit var adapter: DeviceAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentDeviceSelectionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = DeviceAdapter(
            onDeviceClick = { device ->
                BluetoothHidService.start(requireContext())
                viewModel.connectDevice(device.address)
                findNavController().navigate(R.id.remoteFragment)
            },
            onUnpairClick = { device ->
                viewModel.unpairDevice(device.address)
            }
        )

        binding.rvDevices.layoutManager = LinearLayoutManager(requireContext())
        binding.rvDevices.adapter = adapter

        binding.btnPairNew.setOnClickListener {
            findNavController().navigate(R.id.deviceDiscoveryFragment)
        }

        binding.btnDistantPair.setOnClickListener {
            findNavController().navigate(R.id.distantDevicePairFragment)
        }

        binding.toolbar.inflateMenu(R.menu.menu_device_selection)
        binding.toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_settings -> {
                    findNavController().navigate(R.id.settingsFragment)
                    true
                }
                else -> false
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.devicesFlow.collectLatest { devices ->
                adapter.submitList(devices)
                if (devices.isEmpty()) {
                    binding.rvDevices.visibility = View.GONE
                    binding.tvEmpty.visibility = View.VISIBLE
                } else {
                    binding.rvDevices.visibility = View.VISIBLE
                    binding.tvEmpty.visibility = View.GONE
                }
            }
        }

        viewModel.findBondedDevices()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}