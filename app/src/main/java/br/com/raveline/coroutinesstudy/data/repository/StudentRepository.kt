package br.com.raveline.coroutinesstudy.data.repository

import br.com.raveline.coroutinesstudy.data.model.Student
import kotlinx.coroutines.delay

class StudentRepository {

    suspend fun getStudentData(): List<Student> {
        val students = arrayListOf<Student>()
        for (i in 1 until 10) {
            delay(1000)
            students.add(Student(i,"Student $i","email_student$i@email.com"))
        }
        return students
    }
}