package com.example.mvvm.ui.main.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvm.data.model.response.Data
import com.example.mvvm.databinding.NameItemBinding
import com.example.mvvm.ui.main.callbacks.OnItemDeleteListener

class NamesAdapter(
    private var nameList: ArrayList<Data>,
    private var onItemDelete: OnItemDeleteListener
): RecyclerView.Adapter<NamesAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = NameItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(nameList[position])
    }

    override fun getItemCount(): Int {
        return nameList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(list: ArrayList<Data>){
        nameList = list
        notifyDataSetChanged()
    }

    inner class Holder(
        private val binding: NameItemBinding
    ):RecyclerView.ViewHolder(binding.root){

        fun bind(user: Data) {
            binding.tvName.text = user.firstName

            binding.ivDelete.setOnClickListener{
                onItemDelete.onItemDelete(user)
            }
        }

    }
}