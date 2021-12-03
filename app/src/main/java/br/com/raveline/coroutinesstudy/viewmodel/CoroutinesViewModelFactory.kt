package br.com.raveline.coroutinesstudy.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CoroutinesViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CoroutinesViewModel::class.java))
            return CoroutinesViewModel() as T
        throw IllegalArgumentException("Wrong view model")
    }
}