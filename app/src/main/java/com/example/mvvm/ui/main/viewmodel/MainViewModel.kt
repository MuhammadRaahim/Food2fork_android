package com.example.mvvm.ui.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.example.mvvm.data.model.Blog
import com.example.mvvm.data.model.response.QuoteListResponse
import com.example.mvvm.data.repositroy.MainRepository
import com.example.mvvm.utils.BaseUtils
import com.horizam.pro.elean.utils.Resource
import kotlinx.coroutines.Dispatchers

class MainViewModel(private val  mainRepository: MainRepository): ViewModel() {

    private val getQuoteRequest = MutableLiveData<String>()


    val  getQuotes = getQuoteRequest.switchMap {
        liveData(Dispatchers.IO){
            emit(Resource.loading(data = null))
            try {
                emit(Resource.success(data = mainRepository.getQuotes()))
            } catch (exception: Exception) {
                val errorMessage = BaseUtils.getError(exception)
                emit(Resource.error(data = null, message = errorMessage))
            }
        }
    }


    fun getQuotes(){
        getQuoteRequest.value = ""
    }

}


