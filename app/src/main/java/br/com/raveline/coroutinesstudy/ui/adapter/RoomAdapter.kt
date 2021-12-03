package br.com.raveline.coroutinesstudy.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import br.com.raveline.coroutinesstudy.data.model.Client
import br.com.raveline.coroutinesstudy.databinding.ItemRoomAdapterBinding
import br.com.raveline.coroutinesstudy.ui.adapter.diffutils.RoomDiffUtil

class RoomAdapter(
    private val clients: List<Client>,
    private val clickListener: (Client) -> Unit,
) : RecyclerView.Adapter<RoomAdapter.MyViewHolder>() {

    var roomList = emptyList<Client>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            ItemRoomAdapterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val client = roomList[position]
        holder.bind(client,clickListener)
    }

    override fun getItemCount(): Int = roomList.size

    fun setClientData(clients: List<Client>) {
        val clientDiffUtil = RoomDiffUtil(roomList, clients)
        val diffUtilResult = DiffUtil.calculateDiff(clientDiffUtil)
        roomList = clients
        diffUtilResult.dispatchUpdatesTo(this)
    }

    inner class MyViewHolder(private val rBinding: ItemRoomAdapterBinding) :
        RecyclerView.ViewHolder(rBinding.root) {
        fun bind(client: Client,clickListener: (Client) -> Unit) {
            rBinding.textViewNameRoomAdapter.text = client.name
            rBinding.textViewEmailRoomAdapter.text = client.email
            rBinding.llRoomAdapter.setOnClickListener{
                clickListener(client)
            }
        }

    }
}