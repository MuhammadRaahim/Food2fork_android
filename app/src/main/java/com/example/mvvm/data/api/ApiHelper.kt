package com.example.mvvm.data.api



class ApiHelper(private val apiService: ApiService) {

    suspend fun getQuotes() = apiService.getQuotes()

}