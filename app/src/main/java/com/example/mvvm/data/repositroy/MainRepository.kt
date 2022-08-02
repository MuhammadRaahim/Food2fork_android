package com.example.mvvm.data.repositroy

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.mvvm.data.api.ApiHelper
import com.example.mvvm.data.pagingsource.QuotePagingSource

class MainRepository(private val apiHelper: ApiHelper) {

    fun getQuotes() = Pager(
        config = PagingConfig(
            pageSize = 20,
            maxSize = 100,
            enablePlaceholders = false
        ),
        pagingSourceFactory = {
            QuotePagingSource(apiHelper)
        }
    ).liveData
}