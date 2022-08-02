package com.example.mvvm.ui.main.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvvm.data.api.ApiHelper
import com.example.mvvm.data.api.RetrofitBuilder
import com.example.mvvm.data.model.response.Data
import com.example.mvvm.databinding.ActivityMainBinding
import com.example.mvvm.ui.base.ViewModelFactory
import com.example.mvvm.ui.main.adapter.MyLoadStateAdapter
import com.example.mvvm.ui.main.adapter.NamesAdapter
import com.example.mvvm.ui.main.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: NamesAdapter
    private lateinit var nameList: ArrayList<Data>

    private val viewModel:MainViewModel by viewModels{
        ViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpUI()
        setRecyclerView()
        setUpObserver()
        setClickListeners()
        executeApi()
    }

    private fun setClickListeners() {
        binding.apply {
            btnSubmit.setOnClickListener{
                executeApi()
            }
        }
    }

    private fun executeApi(){
        if (viewModel.getQuotes.value == null) {
            viewModel.getQuotes()
        }
    }

    private fun setUpObserver() {
        viewModel.getQuotes.observe(this){
            adapter.submitData(lifecycle, it)
        }
    }


    private fun setUpUI() {
        nameList = ArrayList()
        adapter = NamesAdapter()
    }

    private fun setRecyclerView() {
        binding.rvNames.let {
            it.setHasFixedSize(true)
            it.layoutManager = LinearLayoutManager(this)
            it.adapter = NamesAdapter()
            it.adapter = adapter.withLoadStateHeaderAndFooter(
                header = MyLoadStateAdapter { adapter.retry() },
                footer = MyLoadStateAdapter { adapter.retry() }
            )
            setAdapterLoadState(adapter)
        }
    }

    private fun setAdapterLoadState(adapter: NamesAdapter) {
        adapter.addLoadStateListener { loadState ->
            binding.apply {
                binding.progressBar.isVisible = loadState.source.refresh is LoadState.Loading
                binding.rvNames.isVisible = loadState.source.refresh is LoadState.NotLoading
                binding.btnSubmit.isVisible = loadState.source.refresh is LoadState.Error
                // no results
                if (loadState.source.refresh is LoadState.NotLoading &&
                    loadState.append.endOfPaginationReached &&
                    adapter.itemCount < 1
                ) {

                    binding.rvNames.isVisible = false
                }
            }
        }
    }




}