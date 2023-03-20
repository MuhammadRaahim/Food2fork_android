package com.instances.food2fork.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.instances.food2fork.R
import com.instances.food2fork.data.model.response.Data
import com.instances.food2fork.databinding.NameItemBinding

class NamesAdapter: PagingDataAdapter<Data,NamesAdapter.Holder>(ITEM_COMPARATOR){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding:NameItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),
            R.layout.name_item, parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    inner class Holder(
        private val binding: NameItemBinding
    ):RecyclerView.ViewHolder(binding.root){

        fun bind(currentItem: Data) {
            binding.data = currentItem
        }

    }

    companion object{
        private val ITEM_COMPARATOR = object : DiffUtil.ItemCallback<Data>() {
            override fun areItemsTheSame(oldItem: Data, newItem: Data) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Data, newItem: Data) =
                oldItem == newItem
        }
    }


}