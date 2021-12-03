package br.com.raveline.coroutinesstudy.data.database.repository

import br.com.raveline.coroutinesstudy.data.database.dao.ClientDao
import br.com.raveline.coroutinesstudy.data.model.Client

class ClientRepository(private val clientDao: ClientDao) {

    val allClients = clientDao.selectAllClients()

    suspend fun insertClient(client: Client):Long {
        return clientDao.insertClient(client)
    }

    suspend fun updateClient(client: Client):Int {
       return clientDao.updateClient(client)
    }

    suspend fun getSingleClient(id: Int): Client = clientDao.selectSingleClient(id)

    suspend fun deleteClient(client: Client):Int {
        return clientDao.deleteClient(client)
    }

    suspend fun deleteAllClients():Int {
       return  clientDao.deleteAllClients()
    }

}