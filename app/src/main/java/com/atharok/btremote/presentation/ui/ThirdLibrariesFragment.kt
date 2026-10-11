package com.atharok.btremote.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.atharok.btremote.databinding.FragmentThirdLibrariesBinding
import com.atharok.btremote.domain.entities.ThirdLibrary

class ThirdLibrariesFragment : Fragment() {

    private var _binding: FragmentThirdLibrariesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentThirdLibrariesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        binding.rvLibraries.layoutManager = LinearLayoutManager(requireContext())
        binding.rvLibraries.adapter = ThirdLibrariesAdapter(ThirdLibrary.entries.toList())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}