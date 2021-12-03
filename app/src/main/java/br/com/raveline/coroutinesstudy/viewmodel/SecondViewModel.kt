package br.com.raveline.coroutinesstudy.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SecondViewModel:ViewModel() {
    val userName = MutableLiveData<String>()

    init {
        userName.postValue("Franklin")
    }
}