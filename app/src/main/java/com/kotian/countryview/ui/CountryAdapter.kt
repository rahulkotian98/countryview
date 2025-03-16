package com.kotian.countryview.ui


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kotian.countryview.databinding.ItemCountryBinding
import com.kotian.countryview.model.Country

class CountryAdapter : ListAdapter<Country, CountryAdapter.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCountryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemCountryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(country: Country) {
            binding.apply {
                tvNameRegion.text = "${country.name.orEmpty()}, ${country.region.orEmpty()}"
                tvCode.text = country.code.orEmpty()
                tvCapital.text = country.capital.orEmpty()
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Country>() {
            override fun areItemsTheSame(oldItem: Country, newItem: Country): Boolean {
                return oldItem.code == newItem.code
            }

            override fun areContentsTheSame(oldItem: Country, newItem: Country): Boolean {
                return oldItem == newItem
            }
        }
    }
}