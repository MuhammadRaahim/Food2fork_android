package com.example.mvvm.utils

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.example.mvvm.data.model.response.ErrorResponse
import com.google.gson.Gson
import retrofit2.HttpException

class BaseUtils {
    companion object{
        var DEVICE_ID: String = ""

        @SuppressLint("ObsoleteSdkInt")
        fun isInternetAvailable(context: Context): Boolean {
            var result = false
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val networkCapabilities = connectivityManager.activeNetwork ?: return false
                val actNw =
                    connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
                result = when {
                    actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                    else -> false
                }
            } else {
                connectivityManager.run {
                    connectivityManager.activeNetworkInfo?.run {
                        result = when (type) {
                            ConnectivityManager.TYPE_WIFI -> true
                            ConnectivityManager.TYPE_MOBILE -> true
                            ConnectivityManager.TYPE_ETHERNET -> true
                            else -> false
                        }

                    }
                }
            }

            return result
        }

        fun getError(exception: Exception): String {
            return try {
                val httpException: HttpException = exception as HttpException
                val errorBody: String = httpException.response()!!.errorBody()!!.string()
                val errorResponse: ErrorResponse =
                    Gson().fromJson(errorBody, ErrorResponse::class.java)
                if (httpException.code() == 401) {
                    //logout
//                    EventBus.getDefault().post(LogoutEvent())
                }
                errorResponse.message
            } catch (e: Exception) {
                exception.message.toString()
            }
        }
    }
}