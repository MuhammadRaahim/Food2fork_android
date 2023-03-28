package com.instances.food2fork.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.instances.food2fork.data.api.ApiHelper
import com.instances.food2fork.data.database.RecipeDatabase
import com.instances.food2fork.data.repositroy.MainRepository
import com.instances.food2fork.ui.main.viewmodel.MainViewModel

class ViewModelFactory(private val apiHelper: ApiHelper, private val database: RecipeDatabase): ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when{
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(MainRepository(apiHelper,database)) as T
            }
            else -> throw IllegalArgumentException("Unknown class name")
        }
    }

}