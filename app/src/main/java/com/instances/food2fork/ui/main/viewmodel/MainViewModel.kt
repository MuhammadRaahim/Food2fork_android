package com.instances.food2fork.ui.main.viewmodel

import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.instances.food2fork.data.repositroy.MainRepository

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


