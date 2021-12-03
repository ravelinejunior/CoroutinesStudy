package br.com.raveline.coroutinesstudy.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SecondNavigationViewModel : ViewModel() {
    var textFromOne = MutableLiveData<String>()
    val textValue: LiveData<String> get() = textFromOne
}