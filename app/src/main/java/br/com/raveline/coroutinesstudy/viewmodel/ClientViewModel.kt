package br.com.raveline.coroutinesstudy.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.raveline.coroutinesstudy.data.database.repository.ClientRepository
import br.com.raveline.coroutinesstudy.data.model.Client
import br.com.raveline.coroutinesstudy.utils.EventLiveData
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class ClientViewModel(
    private val clientRepository: ClientRepository
) : ViewModel() {
    val inputName = MutableLiveData<String>()
    val inputEmail = MutableLiveData<String>()

    private var isUpdateOrDelete = false
    private var clientUpdateOrDelete: Client? = null

    val clients = clientRepository.allClients

    private val statusMessage = MutableLiveData<EventLiveData<String>>()
    val messageLiveData: LiveData<EventLiveData<String>> get() = statusMessage

    fun initUpdateOrDelete(client: Client) {
        inputName.postValue(client.name)
        inputEmail.postValue(client.email)

        isUpdateOrDelete = true
        clientUpdateOrDelete = client

    }


    fun saveOrUpdate() {
        if (isUpdateOrDelete) {
            clientUpdateOrDelete?.name = inputName.value.toString()
            clientUpdateOrDelete?.email = inputEmail.value.toString()
            clientUpdateOrDelete?.let { updateClient(it) }

        } else {

            if (inputName.value != null && inputEmail.value != null) {
                val name = inputName.value!!
                val email = inputEmail.value!!

                insertClient(Client(0, name, email))
                inputName.postValue("")
                inputEmail.postValue("")
            } else {
                statusMessage.postValue(EventLiveData("Fields must be filled!"))
            }

        }
    }

    fun clearAllOrDelete() {
        if (isUpdateOrDelete) {
            clientUpdateOrDelete?.let { deleteSingleClient(it) }
        } else {
            deleteAllClients()
        }
    }

    private fun insertClient(client: Client) =
        viewModelScope.launch(IO) {
            val rowId = clientRepository.insertClient(client)
            if (rowId > -1) {
                statusMessage.postValue(EventLiveData("Client Added Successfully!"))
            } else {
                statusMessage.postValue(EventLiveData("Client Was Not Added Successfully!"))
            }
        }

    private fun updateClient(client: Client) =
        viewModelScope.launch(IO) {
            val rowId = clientRepository.updateClient(client)
            if (rowId > 0) {
                inputName.postValue("")
                inputEmail.postValue("")

                isUpdateOrDelete = false
                clientUpdateOrDelete = null

                statusMessage.postValue(EventLiveData("Client $rowId Updated Successfully!"))
            } else {
                statusMessage.postValue(EventLiveData("Client $rowId Was Not Updated Successfully!"))

            }

        }

    private fun deleteAllClients() = viewModelScope.launch(IO) {
        val rowsNumber = clientRepository.deleteAllClients()

        if (rowsNumber > 0)
            statusMessage.postValue(EventLiveData("$rowsNumber Clients Deleted Successfully!"))
        else statusMessage.postValue(EventLiveData("Error deleting all data"))
    }

    fun getSingleClient(id: Int): Client? {
        var client: Client? = null
        viewModelScope.launch(IO) {
            client = clientRepository.getSingleClient(id)
        }
        return client
    }

    private fun deleteSingleClient(client: Client) = viewModelScope.launch(IO) {
        val rowId = clientRepository.deleteClient(client)

        if (rowId > -1) {
            inputName.postValue("")
            inputEmail.postValue("")

            isUpdateOrDelete = false
            clientUpdateOrDelete = null

            statusMessage.postValue(EventLiveData("Client Deleted Successfully!"))
        } else {
            statusMessage.postValue(EventLiveData("Client Was Not Deleted Successfully!"))
        }

    }


}