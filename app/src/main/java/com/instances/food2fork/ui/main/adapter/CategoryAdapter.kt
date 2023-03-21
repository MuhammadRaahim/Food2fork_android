package com.instances.food2fork.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.instances.food2fork.databinding.ItemCategoriesBinding
import com.instances.food2fork.ui.main.callbacks.OnCategoryClickListener
import com.instances.food2fork.utils.Constants.Companion.CATEGORY_LIST


class CategoryAdapter(private val listener: OnCategoryClickListener): RecyclerView.Adapter<CategoryAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding: ItemCategoriesBinding = ItemCategoriesBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return CATEGORY_LIST.size
    }

    inner class Holder(
        binding: ItemCategoriesBinding
    ):RecyclerView.ViewHolder(binding.root){
        var binding: ItemCategoriesBinding = binding
        fun bind(position: Int) {
            val category = CATEGORY_LIST[position]
            binding.tvCaterory.text = category

            itemView.setOnClickListener {
                listener.onCategoryClick(category)
            }
        }
    }
}