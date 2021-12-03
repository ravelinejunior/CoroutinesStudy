package br.com.raveline.coroutinesstudy.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ThirdNavigationViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ThirdNavigationViewModel::class.java)) return ThirdNavigationViewModel() as T
        throw IllegalAccessException("ViewModel not found")

    }
}