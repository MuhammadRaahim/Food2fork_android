package com.instances.food2fork.data.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.instances.food2fork.data.api.ApiHelper
import com.instances.food2fork.data.model.response.Results
import com.instances.food2fork.utils.Constants.Companion.STARTING_PAGE_INDEX
import retrofit2.HttpException
import java.io.IOException

class RecipePagingSource(private val apiHelper: ApiHelper,
                         private val query: String)
    : PagingSource<Int, Results>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Results> {

        val position = params.key ?: STARTING_PAGE_INDEX

        return try {
            val response = apiHelper.getQuotes(position,query)
            val recipes = response.results
            LoadResult.Page(
                data = recipes,
                prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (recipes.isEmpty()) null else position + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Results>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }


}