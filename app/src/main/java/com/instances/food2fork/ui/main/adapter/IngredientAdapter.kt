package com.instances.food2fork.ui.main.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.instances.food2fork.databinding.IngredientItemBinding
import com.instances.food2fork.databinding.ItemCategoriesBinding


class IngredientAdapter(
    private var ingredientList: ArrayList<String>
): RecyclerView.Adapter<IngredientAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding: IngredientItemBinding = IngredientItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return ingredientList.size
    }

    inner class Holder(
        binding: IngredientItemBinding
    ):RecyclerView.ViewHolder(binding.root){
        var binding: IngredientItemBinding = binding
        fun bind(position: Int) {
            val ingredient = ingredientList[position]
            binding.tvIngredient.text = ingredient
        }
    }
}