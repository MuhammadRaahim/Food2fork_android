package com.example.mvvm.ui.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mvvm.data.model.Blog

class MainViewModel: ViewModel() {

    var _nameList = MutableLiveData<ArrayList<Blog>>()
    var nameList = arrayListOf<Blog>()


     fun add(name: Blog){
        nameList.add(name)
        _nameList.value = nameList
    }

    fun remove(name: Blog){
        nameList.remove(name)
        _nameList.value=nameList
    }

}