package com.instances.food2fork.data.api

import com.instances.food2fork.data.model.response.QuoteListResponse
import com.instances.food2fork.data.model.response.RecipesResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {
    @GET("recipe/search/")
    suspend fun getRecipes(@Query("page") page: Int, @Query("query") query: String) : RecipesResponse

}