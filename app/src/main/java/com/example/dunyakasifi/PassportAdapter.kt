package com.example.dunyakasifi

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dunyakasifi.databinding.ItemPassportBinding

class PassportAdapter(
    private val passportItems: List<PassportItem>,
    private val onCountryClick: (PassportItem) -> Unit
) : RecyclerView.Adapter<PassportAdapter.PassportViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PassportViewHolder {
        val binding = ItemPassportBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PassportViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PassportViewHolder, position: Int) {
        holder.bind(passportItems[position])
        val item = passportItems[position]
        if (item.isVisited) {
            holder.itemView.setOnClickListener { onCountryClick(item) }
        } else {
            holder.itemView.setOnClickListener(null)
        }
    }

    override fun getItemCount(): Int = passportItems.size

    inner class PassportViewHolder(
        private val binding: ItemPassportBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: PassportItem) {
            binding.countryFlag.setImageResource(item.flagResId)
            binding.countryName.text = item.countryName
            
            if (item.isVisited) {
                binding.statusText.text = "✅ Ziyaret Edildi"
                binding.statusText.setTextColor(binding.root.context.getColor(android.R.color.holo_green_dark))
                binding.root.alpha = 1.0f
            } else {
                binding.statusText.text = "❌ Henüz Ziyaret Edilmedi"
                binding.statusText.setTextColor(binding.root.context.getColor(android.R.color.holo_red_dark))
                binding.root.alpha = 0.6f
            }
        }
    }
} 