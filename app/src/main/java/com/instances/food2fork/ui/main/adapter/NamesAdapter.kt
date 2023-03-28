package com.instances.food2fork.ui.main.adapter

import android.view.LayoutInflater
import android.view.OnReceiveContentListener
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.instances.food2fork.R
import com.instances.food2fork.data.model.response.Results
import com.instances.food2fork.databinding.ItemCategoriesBinding
import com.instances.food2fork.databinding.ItemRecipeBinding
import com.instances.food2fork.databinding.NameItemBinding
import com.instances.food2fork.ui.main.callbacks.OnRecipeClickListener

class NamesAdapter(
    private  var listener: OnRecipeClickListener
    ): PagingDataAdapter<Results,NamesAdapter.Holder>(ITEM_COMPARATOR){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding: ItemRecipeBinding = ItemRecipeBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val recipe = getItem(position)
        if (recipe != null) {
            holder.bind(recipe)
        }
    }

    inner class Holder(
        private val binding: ItemRecipeBinding
    ):RecyclerView.ViewHolder(binding.root){

        fun bind(recipe: Results) {
            binding.tvRecipe.text = recipe.title
            binding.tvRecipeChief.text = recipe.publisher
            if (recipe.featuredImage != null) {
                Glide.with(itemView)
                    .load(recipe.featuredImage)
                    .error(R.drawable.img_app_logo)
                    .into(binding.ivRecipe)
            }
            itemView.setOnClickListener {
                listener.onRecipeClick(recipe)
            }
        }

    }

    companion object{
        private val ITEM_COMPARATOR = object : DiffUtil.ItemCallback<Results>() {
            override fun areItemsTheSame(oldItem: Results, newItem: Results) =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: Results, newItem: Results) =
                oldItem == newItem
        }
    }


}