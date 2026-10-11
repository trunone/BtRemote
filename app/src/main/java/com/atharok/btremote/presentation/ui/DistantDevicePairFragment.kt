package com.atharok.btremote.presentation.ui

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.atharok.btremote.databinding.FragmentDistantDevicePairBinding
import com.atharok.btremote.presentation.viewmodel.AppScopeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class DistantDevicePairFragment : Fragment() {

    private var _binding: FragmentDistantDevicePairBinding? = null
    private val binding get() = _binding!!

    private val appScopeViewModel: AppScopeViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentDistantDevicePairBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        binding.tvDeviceName.text = getString(
            com.atharok.btremote.R.string.name_of_this_device,
            appScopeViewModel.localDeviceName
        )

        binding.btnOpenBtSettings.setOnClickListener {
            startActivity(Intent(Settings.ACTION_BLUETOOTH_SETTINGS))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}