package com.example.mvvm.ui.main.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvm.data.model.response.Data
import com.example.mvvm.databinding.NameItemBinding
import com.example.mvvm.ui.main.callbacks.OnItemDeleteListener
import kotlinx.coroutines.NonDisposableHandle
import kotlinx.coroutines.NonDisposableHandle.parent

class NamesAdapter: PagingDataAdapter<Data,NamesAdapter.Holder>(ITEM_COMPARATOR){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = NameItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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
            binding.tvName.text = currentItem.firstName
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