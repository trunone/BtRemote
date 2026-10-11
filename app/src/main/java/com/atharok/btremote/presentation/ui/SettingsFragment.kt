package com.atharok.btremote.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.atharok.btremote.R
import com.atharok.btremote.databinding.FragmentSettingsBinding
import com.atharok.btremote.presentation.viewmodel.SettingsViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SettingsViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        binding.btnThirdLibraries.setOnClickListener {
            findNavController().navigate(R.id.thirdLibrariesFragment)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.appearanceSettingsFlow.collectLatest { settings ->
                binding.switchOled.isChecked = settings.useBlackColorForDarkTheme
            }
        }

        binding.switchOled.setOnCheckedChangeListener { _, isChecked ->
            viewModel.setUseBlackColorForDarkTheme(isChecked)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}