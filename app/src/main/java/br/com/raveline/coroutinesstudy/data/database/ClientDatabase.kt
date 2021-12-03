package br.com.raveline.coroutinesstudy.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.com.raveline.coroutinesstudy.data.database.dao.ClientDao
import br.com.raveline.coroutinesstudy.data.model.Client

@Database(entities = [Client::class], version = 1, exportSchema = false)
abstract class ClientDatabase : RoomDatabase() {
    abstract val clientDao: ClientDao

    companion object {
        @Volatile
        private var INSTANCE_CLIENT_ROOM: ClientDatabase? = null
        fun getInstance(context: Context): ClientDatabase {
            synchronized(this) {
                var instance = INSTANCE_CLIENT_ROOM
                if (instance == null) {
                    instance =
                        Room.databaseBuilder(context, ClientDatabase::class.java, "Client_Db")
                            .fallbackToDestructiveMigration()
                            .build()
                }
                return instance
            }
        }
    }
}