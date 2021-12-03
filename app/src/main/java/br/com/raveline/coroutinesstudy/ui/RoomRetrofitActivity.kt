package br.com.raveline.coroutinesstudy.ui

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.animation.OvershootInterpolator
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import br.com.raveline.coroutinesstudy.R
import br.com.raveline.coroutinesstudy.data.database.ClientDatabase
import br.com.raveline.coroutinesstudy.data.database.repository.ClientRepository
import br.com.raveline.coroutinesstudy.data.model.Client
import br.com.raveline.coroutinesstudy.databinding.ActivityRoomRetrofitBinding
import br.com.raveline.coroutinesstudy.ui.adapter.RoomAdapter
import br.com.raveline.coroutinesstudy.viewmodel.ClientViewModel
import br.com.raveline.coroutinesstudy.viewmodel.ClientViewModelFactory
import jp.wasabeef.recyclerview.animators.SlideInRightAnimator
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class RoomRetrofitActivity : AppCompatActivity() {
    private var _binding: ActivityRoomRetrofitBinding? = null
    private val roomRetrofitBinding get() = _binding!!

    private lateinit var clientViewModel: ClientViewModel
    private lateinit var clientViewModelFactory: ClientViewModelFactory

    private lateinit var clientAdapter: RoomAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this, R.layout.activity_room_retrofit)
        roomRetrofitBinding.lifecycleOwner = this

        val clientDao = ClientDatabase.getInstance(this).clientDao
        val clientRepository = ClientRepository(clientDao)

        clientViewModelFactory = ClientViewModelFactory(clientRepository)
        clientViewModel =
            ViewModelProvider(this, clientViewModelFactory)[ClientViewModel::class.java]

        roomRetrofitBinding.clientViewModel = clientViewModel
        setupRecyclerView()

        getAllClients()

        clientViewModel.messageLiveData.observe(this, { event ->
            event.getContentIfNotHandled()?.let { message ->
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            }
        })

    }

    private fun getAllClients() {
        lifecycleScope.launch(Main) {
            delay(1000)

            roomRetrofitBinding.progressBarRoomActivity.visibility = GONE
            roomRetrofitBinding.cardViewRoomActivity.visibility = VISIBLE

        }
        clientViewModel.clients.observe(this, { clients ->
            clientAdapter = RoomAdapter(clients) { selectedItem: Client ->
                listItemClicked(selectedItem)
            }
            clientAdapter.setClientData(clients)
            setupRecyclerAdapter()
        })

    }

    private fun listItemClicked(client: Client) {
        clientViewModel.initUpdateOrDelete(client)
    }

    private fun setupRecyclerView() {
        roomRetrofitBinding.recyclervRoomActivity.apply {
            setHasFixedSize(true)
            setHasTransientState(true)
            itemAnimator = SlideInRightAnimator(OvershootInterpolator(5f))

        }
    }

    private fun setupRecyclerAdapter() {
        roomRetrofitBinding.recyclervRoomActivity.apply {
            adapter = clientAdapter
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}