package br.com.raveline.coroutinesstudy.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import br.com.raveline.coroutinesstudy.R
import br.com.raveline.coroutinesstudy.databinding.ActivityCoroutinesBinding
import br.com.raveline.coroutinesstudy.viewmodel.CoroutinesViewModel
import br.com.raveline.coroutinesstudy.viewmodel.CoroutinesViewModelFactory
import br.com.raveline.coroutinesstudy.viewmodel.MainViewModel
import br.com.raveline.coroutinesstudy.viewmodel.MainViewModelFactory
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main

class CoroutinesActivity : AppCompatActivity() {
    private var _binding: ActivityCoroutinesBinding? = null
    private val coroutinesBinding get() = _binding!!

    private val mainViewModelFactory = MainViewModelFactory(120)
    private val mainViewModel: MainViewModel by viewModels { mainViewModelFactory }

    private val coroutinesViewModelFactory = CoroutinesViewModelFactory()
    private val coroutinesViewModel: CoroutinesViewModel by viewModels { coroutinesViewModelFactory }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this, R.layout.activity_coroutines)
        coroutinesBinding.lifecycleOwner = this
        lifeCycleMethods()


        //TESTANDO VIEWMODELLIFECYCLE
        //coroutinesViewModel.getUserData()
        coroutinesViewModel.students.observe(this, { students ->
            students?.forEach {
                Log.i("CoroutinesActivity", "Student data\nName:${it.name}, Email:${it.email})")
            }
        })



        coroutinesBinding.buttonCoroutinesMain.setOnClickListener {
            lifecycleScope.launch(IO) {
                //downloadData()
                CoroutineScope(IO).launch {
                    Log.i(
                        "CoroutinesActivity",
                        "Valor da função: ${mainViewModel.getTotalUserCount()}"
                    )
                }
                coroutineScope {
                    launch(Main) {
                        coroutinesBinding.textViewDownloadCoroutinesMain.apply {
                            visibility = VISIBLE

                            text =
                                "Current value is: ${mainViewModel.getOtherDataNumber()}"
                        }

                    }
                }
            }
        }

        coroutinesBinding.buttonGoToMainCoroutines.setOnClickListener {
            val intent = Intent(this, RoomRetrofitActivity::class.java)
            startActivity(intent)
        }

        coroutinesBinding.textViewGoRetrofitCoroutinesMain.setOnClickListener {
            val intent = Intent(this, RetrofitActivity::class.java)
            startActivity(intent)
        }
    }

    @SuppressLint("SetTextI18n")
    private suspend fun downloadData() {
        withContext(Main) {
            coroutinesBinding.textViewDownloadCoroutinesMain.visibility = VISIBLE
            for (i in 1 until (100)) {
                // Log.i("CoroutinesActivity", "downloadData: $i in ${Thread.currentThread().name}")
                delay(20)
                coroutinesBinding.textViewDownloadCoroutinesMain.text = "Loading $i%"
            }
            coroutinesBinding.textViewDownloadCoroutinesMain.visibility = GONE
        }


    }

    private fun lifeCycleMethods() {
        lifecycleScope.launch(IO) {
            Log.d("lifecycleScope", "thread is: ${Thread.currentThread().name}")
        }
        lifecycleScope.launchWhenCreated {
            Log.d("lifecycleScope", "thread is: ${Thread.currentThread().name} -- CREATED")
        }
        lifecycleScope.launchWhenStarted {
            Log.d("lifecycleScope", "thread is: ${Thread.currentThread().name} -- STARTED")
        }
        lifecycleScope.launchWhenResumed {
            Log.d("lifecycleScope", "thread is: ${Thread.currentThread().name} -- RESUMED")
        }
    }

    override fun onStart() {
        super.onStart()

        //CHAMA A PRIMEIRA FUNÇÃO , DEPOIS A SEGUNDA ...
        CoroutineScope(IO).launch {
            val stockOne = getStock()
            val stockTwo = getStockAnother()
            val totalStock = stockOne + stockTwo
            Log.i("CoroutinesActivity", "Get Total Stocks: $totalStock")
        }

        //USA O AWAIT PARA AGUARDAR E VERIFICAR QUAL FUNÇÃO SERÁ ACIONADA PRIMEIRO E RETORNAR VALORES ANTES
        CoroutineScope(IO).launch {
            val stockOne = async(IO) { getStock() }
            val stockTwo = async(IO) { getStockAnother() }
            val totalStock = stockOne.await() + stockTwo.await()
            Log.i("CoroutinesActivity", "Get Total Stocks from await: $totalStock")
        }


    }

    private suspend fun getStock(): Int {
        delay(10000)
        Log.i("CoroutinesActivity", "getStock: 1 return")
        return 4000
    }

    private suspend fun getStockAnother(): Int {
        delay(8000)
        Log.i("CoroutinesActivity", "getStock: 2 return")
        return 3000
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}