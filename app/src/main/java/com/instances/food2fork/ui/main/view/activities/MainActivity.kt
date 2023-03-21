package com.instances.food2fork.ui.main.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.instances.food2fork.R
import com.instances.food2fork.data.api.ApiHelper
import com.instances.food2fork.data.api.RetrofitBuilder
import com.instances.food2fork.data.model.response.Data
import com.instances.food2fork.data.model.response.Results
import com.instances.food2fork.databinding.ActivityMainBinding
import com.instances.food2fork.ui.base.ViewModelFactory
import com.instances.food2fork.ui.main.adapter.MyLoadStateAdapter
import com.instances.food2fork.ui.main.adapter.NamesAdapter
import com.instances.food2fork.ui.main.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: NamesAdapter
    private lateinit var nameList: ArrayList<Results>

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
        if (viewModel.getRecipes.value == null) {
            viewModel.getRecipes("beef")
        }
    }

    private fun setUpObserver() {
        viewModel.getRecipes.observe(this){
            adapter.submitData(lifecycle, it)
        }
    }


    private fun setUpUI() {
        nameList = ArrayList()
    }

    private fun setRecyclerView() {
        binding.rvNames.let {
            it.setHasFixedSize(true)
            it.layoutManager = LinearLayoutManager(this)

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