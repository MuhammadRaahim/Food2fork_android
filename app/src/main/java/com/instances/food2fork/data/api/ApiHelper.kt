package com.instances.food2fork.data.api



class ApiHelper(private val apiService: ApiService) {
    suspend fun getQuotes(pageNo: Int, query: String) = apiService.getRecipes(page = pageNo, query = query)
}