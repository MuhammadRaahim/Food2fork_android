package com.instances.food2fork.ui.main.view.activities

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.instances.food2fork.R
import com.instances.food2fork.data.model.response.Results
import com.instances.food2fork.databinding.ActivityDetailBinding
import com.instances.food2fork.ui.main.adapter.IngredientAdapter
import com.instances.food2fork.utils.Constants.Companion.RECIPE
import java.io.Serializable

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var adapter: IngredientAdapter
    private lateinit var result: Results

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpUi()
        setIngredientRecyclerView()

    }

    private fun setUpUi() {
        result = intent.serializable(RECIPE)!!
        binding.tvRecipe.text = result.title
        binding.tvRecipeChief.text = "Updated ".plus(result.dateUpdated)
        if (result.featuredImage != null) {
            Glide.with(this)
                .load(result.featuredImage)
                .error(R.drawable.img_app_logo)
                .into(binding.ivRecipe)
        }

    }

    private inline fun <reified T : Serializable> Intent.serializable(key: String): T? = when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> getSerializableExtra(key, T::class.java)
        else -> @Suppress("DEPRECATION") getSerializableExtra(key) as? T
    }

    private fun setIngredientRecyclerView() {
        binding.rvIngredient.let {
            it.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL,false)
            adapter = IngredientAdapter(result.ingredients)
            it.adapter = adapter
        }
    }
}