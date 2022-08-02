package com.example.mvvm.ui.main.viewmodel

import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.example.mvvm.data.repositroy.MainRepository
import com.example.mvvm.utils.BaseUtils
import com.horizam.pro.elean.utils.Resource
import kotlinx.coroutines.Dispatchers

class MainViewModel(private val  mainRepository: MainRepository): ViewModel() {

    private val getQuoteRequest = MutableLiveData<String>()


    val getQuotes = getQuoteRequest.switchMap {
        mainRepository.getQuotes().cachedIn(viewModelScope)
    }


//    val  getQuotes = getQuoteRequest.switchMap {
//        liveData(Dispatchers.IO){
//            emit(Resource.loading(data = null))
//            try {
//                emit(Resource.success(data = mainRepository.getQuotes()))
//            } catch (exception: Exception) {
//                val errorMessage = BaseUtils.getError(exception)
//                emit(Resource.error(data = null, message = errorMessage))
//            }
//        }
//    }


    fun getQuotes(){
        getQuoteRequest.value = ""
    }

}


