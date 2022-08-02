package com.example.mvvm.data.api



class ApiHelper(private val apiService: ApiService) {

    suspend fun getQuotes(pageNo: Int) = apiService.getQuotes(pageNumber = pageNo)

}