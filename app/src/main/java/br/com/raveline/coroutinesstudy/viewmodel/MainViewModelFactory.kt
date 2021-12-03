package br.com.raveline.coroutinesstudy.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class MainViewModelFactory(
    private val startingTotal:Int
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
       if (modelClass.isAssignableFrom(MainViewModel::class.java))
           return MainViewModel(startingTotal) as T
        throw IllegalArgumentException("Unknown View Model class")
    }
}