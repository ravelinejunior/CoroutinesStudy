package br.com.raveline.coroutinesstudy.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import br.com.raveline.coroutinesstudy.R
import br.com.raveline.coroutinesstudy.databinding.ActivitySecondMainBinding
import br.com.raveline.coroutinesstudy.viewmodel.SecondViewModel
import br.com.raveline.coroutinesstudy.viewmodel.SecondViewModelFactory

class SecondMainActivity : AppCompatActivity() {
    private var _binding: ActivitySecondMainBinding? = null
    private val secondMainBinding get() = _binding!!

    private val secondViewModelFactory:SecondViewModelFactory = SecondViewModelFactory()
    private val secondViewModel by viewModels<SecondViewModel> { secondViewModelFactory }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this, R.layout.activity_second_main)
        secondMainBinding.lifecycleOwner = this
        secondMainBinding.viewModel = secondViewModel

        secondMainBinding.buttonGoToSecondMain.setOnClickListener {
            val intent = Intent(this,ThirdNavigationActivity::class.java)
            startActivity(intent)
        }

    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}