package br.com.raveline.coroutinesstudy.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.raveline.coroutinesstudy.data.database.repository.ClientRepository

class ClientViewModelFactory(
    private val clientRepository: ClientRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ClientViewModel::class.java))
            return ClientViewModel(
                clientRepository
            ) as T
        throw IllegalArgumentException("Wrong view model")
    }
}