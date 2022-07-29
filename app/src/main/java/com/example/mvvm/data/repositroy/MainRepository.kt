package com.example.mvvm.data.repositroy

import com.example.mvvm.data.api.ApiHelper

class MainRepository(private val apiHelper: ApiHelper) {

    suspend fun getQuotes() = apiHelper.getQuotes()
}