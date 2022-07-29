package com.example.mvvm.ui.main.view.activities

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvm.data.api.ApiHelper
import com.example.mvvm.data.api.RetrofitBuilder
import com.example.mvvm.data.model.Blog
import com.example.mvvm.data.model.response.Data
import com.example.mvvm.data.model.response.QuoteListResponse
import com.example.mvvm.databinding.ActivityMainBinding
import com.example.mvvm.ui.base.ViewModelFactory
import com.example.mvvm.ui.main.adapter.NamesAdapter
import com.example.mvvm.ui.main.callbacks.OnItemDeleteListener
import com.example.mvvm.ui.main.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar
import com.horizam.pro.elean.utils.Status

class MainActivity : AppCompatActivity(), OnItemDeleteListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: NamesAdapter
    private lateinit var nameList: ArrayList<Data>

    private val viewModel by viewModels<MainViewModel>{
        ViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
        setRecyclerView()
        setUpObserver()
        setClickListeners()
    }

    private fun setClickListeners() {
        binding.apply {
            btnSubmit.setOnClickListener{
              viewModel.getQuotes()
            }
        }
    }

    private fun setUpObserver() {
        viewModel.getQuotes.observe(this, Observer{
            it.let { resource ->
                when(resource.status){
                    Status.SUCCESS -> {
                        binding.progressBar.isVisible = false
                        handleResponse(it.data)
                    }
                    Status.LOADING -> {
                        binding.progressBar.isVisible = true
                    }
                    Status.ERROR -> {
                        binding.progressBar.isVisible = false
                        Snackbar.make(findViewById(android.R.id.content),it.message.toString()
                            ,Snackbar.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }

    private fun handleResponse(data: QuoteListResponse?) {
        if (data != null){
            nameList = data.data
            adapter.updateList(nameList)
        }
    }

    private fun initViews() {
        nameList = ArrayList()
    }

    private fun setRecyclerView() {
        binding.rvNames.layoutManager =LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        adapter = NamesAdapter(nameList,this)
        binding.rvNames.adapter = adapter
    }

    override fun <T> onItemDelete(item: T) {
        if (item is Data){
            nameList.remove(item)
            adapter.updateList(nameList)
        }
    }


}