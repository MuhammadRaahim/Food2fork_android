package com.instances.food2fork.ui.main.callbacks

import com.instances.food2fork.data.model.response.Results

interface OnRecipeClickListener {
    fun onRecipeClick(recipe: Results)
}