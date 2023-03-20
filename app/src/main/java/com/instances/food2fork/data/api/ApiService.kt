package com.instances.food2fork.data.api

import com.instances.food2fork.data.model.response.QuoteListResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {

    @GET("users")
    suspend fun getQuotes(@Query("page") pageNumber: Int) : QuoteListResponse

}