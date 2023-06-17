package com.pratwib.consumzy2.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pratwib.consumzy2.database.Category
import com.pratwib.consumzy2.databinding.ItemCategoryBinding

class CategoryAdapter(private val list: List<Category>) :
    RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = list[position]

        holder.binding.tvCategoryName.text = category.name
        holder.binding.ivCategoryColor.setBackgroundColor(category.color)
    }
}