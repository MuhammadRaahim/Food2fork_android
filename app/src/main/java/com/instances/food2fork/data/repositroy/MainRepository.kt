package com.instances.food2fork.data.repositroy

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.instances.food2fork.data.api.ApiHelper
import com.instances.food2fork.data.pagingsource.QuotePagingSource

class MainRepository(private val apiHelper: ApiHelper) {

    fun getQuotes(query: String) = Pager(
        config = PagingConfig(
            pageSize = 1,
            maxSize = 30,
            enablePlaceholders = false
        ),
        pagingSourceFactory = {
            QuotePagingSource(apiHelper,query)
        }
    ).liveData
}