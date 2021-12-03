package br.com.raveline.coroutinesstudy.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*

class MainViewModel(startingTotal: Int) : ViewModel() {
    private var text = ""
    private var number = MutableLiveData<Int>()
    val totalNumber: LiveData<Int> get() = number
    private lateinit var deferred:Deferred<Int>

    init {
        number.postValue(startingTotal)
    }

    fun getCurrentText(): String = text

    fun getUpdatedText(value: String): String {
        text = value
        return text
    }

    fun getCurrentValue(): Int? = number.value

    fun sumNumber(value: String? = "0") {
        number.postValue(value?.toInt()?.let { number.value?.plus(it) })
    }

    suspend fun getTotalUserCount(): Int {
        var count = 0

        //Não funciona corretamente, pois o retorno é mais rapido e nao consegue setar o valor
        CoroutineScope(Dispatchers.IO).launch {
            delay(1000)
            count = 550
        }

        //Funciona pois aguarda a funcionalidade ser executada
         deferred = CoroutineScope(Dispatchers.IO).async {
            delay(3000)
            return@async 1500
        }

        return count + deferred.await()
    }

    suspend fun getOtherDataNumber():Int{
        var count = 0

        coroutineScope {
            launch(Dispatchers.IO) {
                delay(1000)
                count += 1250
            }
            deferred = async(Dispatchers.IO) {
                delay(3000)
                return@async 2590
            }
        }

        return count + deferred.await()
    }
}