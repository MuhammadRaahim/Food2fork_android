package com.instances.food2fork.ui.main.viewmodel

import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.instances.food2fork.data.repositroy.MainRepository

class MainViewModel(private val  mainRepository: MainRepository): ViewModel() {

    private val getQuoteRequest = MutableLiveData<String>()


    val getRecipes = getQuoteRequest.switchMap {
        mainRepository.getQuotes(it).cachedIn(viewModelScope)
    }

    fun getRecipes(query: String){
        getQuoteRequest.value = query
    }

}


