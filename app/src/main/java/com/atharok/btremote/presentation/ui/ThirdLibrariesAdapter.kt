package com.atharok.btremote.presentation.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.atharok.btremote.databinding.ItemThirdLibraryBinding
import com.atharok.btremote.domain.entities.ThirdLibrary

class ThirdLibrariesAdapter(
    private val libraries: List<ThirdLibrary>
) : RecyclerView.Adapter<ThirdLibrariesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemThirdLibraryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(libraries[position])
    }

    override fun getItemCount(): Int = libraries.size

    inner class ViewHolder(private val binding: ItemThirdLibraryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(library: ThirdLibrary) {
            binding.tvLibraryName.setText(library.title)
            binding.tvLibraryId.setText(library.id)
            binding.tvLibraryLicense.setText(library.license)
        }
    }
}