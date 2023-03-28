package com.instances.food2fork.ui.main.view.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.instances.food2fork.data.api.ApiHelper
import com.instances.food2fork.data.api.RetrofitBuilder
import com.instances.food2fork.data.database.RecipeDatabase
import com.instances.food2fork.data.model.response.Results
import com.instances.food2fork.databinding.ActivityHomeBinding
import com.instances.food2fork.ui.base.ViewModelFactory
import com.instances.food2fork.ui.main.adapter.CategoryAdapter
import com.instances.food2fork.ui.main.adapter.MyLoadStateAdapter
import com.instances.food2fork.ui.main.adapter.NamesAdapter
import com.instances.food2fork.ui.main.callbacks.OnCategoryClickListener
import com.instances.food2fork.ui.main.callbacks.OnRecipeClickListener
import com.instances.food2fork.ui.main.viewmodel.MainViewModel
import com.instances.food2fork.utils.BaseUtils.Companion.isInternetAvailable
import com.instances.food2fork.utils.Constants.Companion.DEFAULT_CATEGORY
import com.instances.food2fork.utils.Constants.Companion.RECIPE

class HomeActivity : AppCompatActivity(), OnCategoryClickListener,OnRecipeClickListener {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var recipeAdapter: NamesAdapter
    private lateinit var recipeList: ArrayList<Results>

    private val database: RecipeDatabase = RecipeDatabase.getDatabase(this)

    private val viewModel: MainViewModel by viewModels{
        ViewModelFactory(ApiHelper(RetrofitBuilder.apiService),database)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setUpUI()
        setCategoryRecyclerView()
        setRecipeRecyclerView()
        setUpObserver()
        executeApi(category = DEFAULT_CATEGORY)
        setSearch()

    }

    private fun setUpUI() {
        recipeList = ArrayList()
        recipeAdapter = NamesAdapter(this)
    }

    private fun executeApi(category: String){
        if (viewModel.getRecipes.value == null) {
            viewModel.getRecipes(category)
        }
    }

    private fun setUpObserver() {
        viewModel.getRecipes.observe(this){
            recipeAdapter.submitData(lifecycle, it)
        }
    }


    private fun setCategoryRecyclerView() {
        binding.rvCategory.let {
            it.layoutManager = LinearLayoutManager(this,RecyclerView.HORIZONTAL,false)
            categoryAdapter = CategoryAdapter(this)
            it.adapter = categoryAdapter
        }
    }

    private fun setRecipeRecyclerView() {
        binding.rvRecipe.let {
            it.setHasFixedSize(true)
            it.layoutManager = LinearLayoutManager(this)
            it.adapter = recipeAdapter
            it.adapter = recipeAdapter.withLoadStateHeaderAndFooter(
                header = MyLoadStateAdapter { recipeAdapter.retry() },
                footer = MyLoadStateAdapter { recipeAdapter.retry() }
            )
            setAdapterLoadState(recipeAdapter)
        }
    }

    private fun setAdapterLoadState(adapter: NamesAdapter) {
        adapter.addLoadStateListener { loadState ->
            binding.apply {
                binding.progressBar.isVisible = loadState.source.refresh is LoadState.Loading
                binding.rvRecipe.isVisible = loadState.source.refresh is LoadState.NotLoading
                binding.progressBar.isVisible = loadState.source.refresh is LoadState.Error
                // no results
                if (loadState.source.refresh is LoadState.NotLoading &&
                    loadState.append.endOfPaginationReached &&
                    adapter.itemCount < 1
                ) {
                    binding.rvRecipe.isVisible = false
                }
            }
        }
    }

    override fun onCategoryClick(category: String) {
        if(isInternetAvailable(this)){
           viewModel.getRecipes(category)
        }
    }

    override fun onRecipeClick(recipe: Results) {
        val intent = Intent(this,DetailActivity::class.java)
        intent.putExtra(RECIPE, recipe)
        startActivity(intent)
    }

    private fun setSearch() {
        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if(isInternetAvailable(this@HomeActivity)){
                    viewModel.getRecipes(s.toString())
                }
            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable) {}
        })
    }
}