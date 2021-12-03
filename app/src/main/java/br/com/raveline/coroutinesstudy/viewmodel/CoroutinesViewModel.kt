package br.com.raveline.coroutinesstudy.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import br.com.raveline.coroutinesstudy.data.model.Student
import br.com.raveline.coroutinesstudy.data.repository.StudentRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO

class CoroutinesViewModel : ViewModel() {

    private val myJob = Job()
    private val myScope = CoroutineScope(Dispatchers.IO + myJob)

    private val studentRepository = StudentRepository()
    var studentMLiveData = MutableLiveData<List<Student>?>()

    var students = liveData(IO){
        val result = studentRepository.getStudentData()
        emit(result)
    }

    fun getUserData() {
        myScope.launch {}

        viewModelScope.launch(IO) {
            var result: List<Student>? = null
            withContext(IO) {
                result = studentRepository.getStudentData()
            }
            studentMLiveData.postValue(result)
        }
    }

    override fun onCleared() {
        super.onCleared()
        myJob.cancel() // obsoleto
    }
}