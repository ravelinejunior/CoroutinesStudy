package br.com.raveline.coroutinesstudy

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import br.com.raveline.coroutinesstudy.databinding.ActivityMainBinding
import br.com.raveline.coroutinesstudy.data.model.Student
import br.com.raveline.coroutinesstudy.ui.SecondMainActivity
import br.com.raveline.coroutinesstudy.viewmodel.MainViewModel
import br.com.raveline.coroutinesstudy.viewmodel.MainViewModelFactory

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val mainBinding get() = _binding!!
    private val mainViewModelFactory: MainViewModelFactory = MainViewModelFactory(150)
    private val mViewModel: MainViewModel by viewModels { mainViewModelFactory }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mainBinding.mViewModel = mViewModel
/*
        mainBinding.buttonSubmitMain.setOnClickListener {

            *//* mViewModel.getUpdatedText(mainBinding.editTextNameMain.text.toString())
             val name = mViewModel.getCurrentText()
             val email = "${mViewModel.getCurrentText()}@gmail.com"

             val student = createStudent(name, email)
             mainBinding.student = student
             *//*
            getSumValue(mainBinding.editTextNumberMain.text.toString().toInt())
        }*/

        mViewModel.totalNumber.observe(this,{ data ->
            mainBinding.textViewMain.text = data.toString()
        })

        mainBinding.buttonSecondMain.setOnClickListener {
            val myIntent:Intent = Intent(this,SecondMainActivity::class.java)
            startActivity(myIntent)
        }

    }

    private fun getSumValue(value: String) {
        mViewModel.sumNumber(value)
    }

    private fun createStudent(name: String, email: String): Student {
        return Student(1, name, email)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}