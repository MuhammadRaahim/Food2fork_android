package com.example.mvvm.data.api

import com.example.mvvm.data.model.response.QuoteListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {

    @GET("users")
    suspend fun getQuotes(@Query("page") pageNumber: Int) : QuoteListResponse

}