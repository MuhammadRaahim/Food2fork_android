package com.instances.food2fork.ui.main.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.instances.food2fork.databinding.ItemRecipeBinding
import com.instances.food2fork.ui.main.view.activities.DetailActivity


class RecipeAdapter(): RecyclerView.Adapter<RecipeAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding: ItemRecipeBinding = ItemRecipeBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return 4
    }


    inner class Holder(
        binding: ItemRecipeBinding
    ):RecyclerView.ViewHolder(binding.root){
        var binding: ItemRecipeBinding = binding
        fun bind(position: Int) {

            itemView.let { it ->
                it.setOnClickListener {
                    it.context.startActivity(Intent(it.context,DetailActivity::class.java))
                }
            }
        }
    }
}