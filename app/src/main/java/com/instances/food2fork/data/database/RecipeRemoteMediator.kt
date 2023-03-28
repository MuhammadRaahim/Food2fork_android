package com.instances.food2fork.data.pagingsource

import androidx.paging.*
import androidx.room.withTransaction
import com.instances.food2fork.data.api.ApiHelper
import com.instances.food2fork.data.database.RecipeDatabase
import com.instances.food2fork.data.model.response.Results

private const val PAGE_SIZE = 30

@ExperimentalPagingApi
class RecipeRemoteMediator(
    private val apiHelper: ApiHelper,
    private val database: RecipeDatabase,
    private val query: String
) : RemoteMediator<Int, Results>() {

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Results>): MediatorResult {
        try {
            val position = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                        ?: return MediatorResult.Success(endOfPaginationReached = true)
                    // get position of last item in database table
                    database.recipeDao().getPosition(lastItem.pk ?: 0)
                }
            }
            // get quotes from API
            val response = apiHelper.getQuotes( position,query)
            val quotes = response.results
            val endOfPaginationReached = quotes.isEmpty()
            // insert quotes into database
            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    database.recipeDao().clearAll()
                }
                database.recipeDao().insertAll(quotes)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }


    private fun getPageNumber(id: Int, pageSize: Int): Int {
        val position = database.recipeDao().getPosition(id)
        return position / pageSize + 1
    }
}
