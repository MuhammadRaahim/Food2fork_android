package com.instances.food2fork.data.repositroy

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.instances.food2fork.App
import com.instances.food2fork.data.api.ApiHelper
import com.instances.food2fork.data.database.RecipeDatabase
import com.instances.food2fork.data.pagingsource.RecipePagingSource
import com.instances.food2fork.data.pagingsource.RecipeRemoteMediator
import com.instances.food2fork.utils.BaseUtils.Companion.isInternetAvailable

class MainRepository(
    private val apiHelper: ApiHelper,
    private val database: RecipeDatabase
) {

    @OptIn(ExperimentalPagingApi::class)
    fun getQuotes(query: String) = Pager(
        config = PagingConfig(
            pageSize = 1,
            maxSize = 30,
            enablePlaceholders = false
        ),
        remoteMediator = RecipeRemoteMediator(apiHelper, database, query),
        pagingSourceFactory = { if (isInternetAvailable(App.getAppContext()!!)) {
            RecipePagingSource(apiHelper, query)
        } else {
            database.recipeDao().getQuotes()
        }}
    ).liveData
}