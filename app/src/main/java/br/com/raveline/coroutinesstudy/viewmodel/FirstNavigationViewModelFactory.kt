package br.com.raveline.coroutinesstudy.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class FirstNavigationViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FirstNavigationViewModel::class.java)) return FirstNavigationViewModel() as T
        throw IllegalAccessException("ViewModel not found")

    }
}