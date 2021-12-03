package br.com.raveline.coroutinesstudy.data.model

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey

@Keep
@Entity(tableName = "Client_tb")
data class Client(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    var name:String,
    var email:String
)
