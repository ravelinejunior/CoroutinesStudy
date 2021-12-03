package br.com.raveline.coroutinesstudy.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import br.com.raveline.coroutinesstudy.data.model.Client

@Dao
sealed interface ClientDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertClient(client: Client):Long

    @Delete
    suspend fun deleteClient(client: Client):Int

    @Update
    suspend fun updateClient(client: Client):Int

    @Query("SELECT * FROM Client_tb ORDER BY ID")
    fun selectAllClients(): LiveData<List<Client>>

    @Query("SELECT * FROM CLIENT_TB WHERE ID = :id ")
    suspend fun selectSingleClient(id: Int): Client

    @Query("DELETE FROM Client_tb")
    suspend fun deleteAllClients() :Int
}