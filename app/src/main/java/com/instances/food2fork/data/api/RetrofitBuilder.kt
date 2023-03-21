package com.instances.food2fork.data.api

import com.instances.food2fork.App
import com.instances.food2fork.utils.BaseUtils
import com.instances.food2fork.utils.Constants
import com.instances.food2fork.utils.PrefManager
import com.google.gson.GsonBuilder
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit

object RetrofitBuilder {

    private val clientBuilder = OkHttpClient.Builder()
    private val okHttpClient = buildClient()

    private fun buildClient(): OkHttpClient {
        val manager = PrefManager(App.getAppContext()!!)
        clientBuilder.addInterceptor(getLoggingInterceptor())
            .addInterceptor { chain ->
                val newRequest: Request = chain.request().newBuilder()
                    .addHeader("Authorization", "Token 9c8b06d329136da358c2d00e76946b0111ce2c48")
                    .build()
                chain.proceed(newRequest)
            }
            .writeTimeout(60, TimeUnit.SECONDS).connectTimeout(5, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
        return clientBuilder.build()
    }

    private fun getLoggingInterceptor(): Interceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }

    private fun getRetrofit(): Retrofit {
        val gson = GsonBuilder()
            .setLenient()
            .create()
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    val apiService: ApiService = getRetrofit().create(ApiService::class.java)

}