package com.instances.food2fork.data.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.instances.food2fork.data.api.ApiHelper
import com.instances.food2fork.data.model.response.Data
import com.instances.food2fork.utils.Constants.Companion.STARTING_PAGE_INDEX
import retrofit2.HttpException
import java.io.IOException

class QuotePagingSource(private val apiHelper: ApiHelper) : PagingSource<Int, Data>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Data> {

        val position = params.key ?: STARTING_PAGE_INDEX

        return try {
            val response = apiHelper.getQuotes(position)
            val users = response.data
            LoadResult.Page(
                data = users,
                prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (users.isEmpty()) null else position + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Data>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }


}