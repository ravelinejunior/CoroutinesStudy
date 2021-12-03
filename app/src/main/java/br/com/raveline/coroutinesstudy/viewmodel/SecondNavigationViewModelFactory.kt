package br.com.raveline.coroutinesstudy.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SecondNavigationViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SecondNavigationViewModel::class.java)) return SecondNavigationViewModel() as T
        throw IllegalAccessException("ViewModel not found")

    }
}