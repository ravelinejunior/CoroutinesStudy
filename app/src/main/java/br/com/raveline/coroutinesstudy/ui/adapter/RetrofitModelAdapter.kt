package br.com.raveline.coroutinesstudy.ui.adapter

import android.app.AlertDialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import br.com.raveline.coroutinesstudy.R
import br.com.raveline.coroutinesstudy.data.remote.model.Albums
import br.com.raveline.coroutinesstudy.data.remote.model.AlbumsItem
import br.com.raveline.coroutinesstudy.data.remote.request.AlbumService
import br.com.raveline.coroutinesstudy.data.remote.request.RetrofitInstance
import br.com.raveline.coroutinesstudy.databinding.ItemRoomAdapterBinding
import br.com.raveline.coroutinesstudy.ui.adapter.diffutils.RoomDiffUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class RetrofitModelAdapter(private val context: Context) :
    RecyclerView.Adapter<RetrofitModelAdapter.MyViewHolder>() {
    var albumsList = emptyList<AlbumsItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            ItemRoomAdapterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val client = albumsList[position]
        val newAlbum = AlbumsItem(10, 1, "Im a single lady, wooooohooo")
        val retrofitService =
            RetrofitInstance.getRetrofitInstance().create(AlbumService::class.java)

        CoroutineScope(Dispatchers.Main).launch {
            val response = retrofitService.getSortedAlbums(client.userId)
            val singleResponse = retrofitService.getAlbum(client.id)
            val postResponse = retrofitService.postAlbum(newAlbum)
            holder.bind(client, response, client.userId, singleResponse, client.id, postResponse)


        }


    }

    override fun getItemCount(): Int = albumsList.size

    fun setAlbumsItemData(albums: Albums) {
        val albumsItemDiffUtil = RoomDiffUtil(albumsList, albums)
        val diffUtilResult = DiffUtil.calculateDiff(albumsItemDiffUtil)
        albumsList = albums
        diffUtilResult.dispatchUpdatesTo(this)
    }

    inner class MyViewHolder(private val rBinding: ItemRoomAdapterBinding) :
        RecyclerView.ViewHolder(rBinding.root) {
        fun bind(
            album: AlbumsItem,
            response: Response<Albums>,
            position: Int,
            singleResponse: Response<AlbumsItem>,
            singleId: Int,
            postResponse: Response<AlbumsItem>

        ) {
            rBinding.textViewNameRoomAdapter.text = "User id: ${album.userId}"
            rBinding.textViewEmailRoomAdapter.text = album.title
            selectItem(response, position, postResponse)
            selectSingleAlbum(singleResponse, singleId)
        }

        private fun selectItem(
            response: Response<Albums>,
            position: Int,
            postResponse: Response<AlbumsItem>
        ) {
            rBinding.llRoomAdapter.setOnLongClickListener {

                try {
                    if (response.isSuccessful) {
                        for (data in response.body()!!) {
                            val alertDialog = AlertDialog.Builder(context)
                            alertDialog.setIcon(R.drawable.avatar_1)
                            alertDialog.setTitle("User id: ${data.userId}")
                            alertDialog.setMessage("Album name: ${data.title}")

                            alertDialog.setPositiveButton("Nice") { dialog, _ ->
                                val receivedAlbumItem = postResponse.body()
                                val result =
                                    " Album Title: ${receivedAlbumItem?.title}" +
                                            " " +
                                            " Album id: ${receivedAlbumItem?.id}" +
                                            " User Id: ${receivedAlbumItem?.userId}"
                                Log.i("RetrofitAdapter", result)
                                dialog.dismiss()
                            }

                            val dialog = alertDialog.create()
                            dialog.show()
                        }
                    }
                } catch (e: Exception) {
                    val alertDialog = AlertDialog.Builder(context)
                    alertDialog.setIcon(R.drawable.avatar_1)
                    alertDialog.setTitle("User id: ${response.body()?.get(position - 1)?.userId}")
                    alertDialog.setMessage(
                        "Album name: ${
                            response.body()?.get(position - 1)?.title
                        }\n${e.message}"
                    )

                    alertDialog.setPositiveButton("Nice") { dialog, _ ->

                        dialog.dismiss()
                    }

                    val dialog = alertDialog.create()
                    dialog.show()
                }
                true
            }
        }

        private fun selectSingleAlbum(
            response: Response<AlbumsItem>,
            position: Int
        ) {
            rBinding.llRoomAdapter.setOnClickListener {

                try {
                    if (response.isSuccessful) {

                        val alertDialog = AlertDialog.Builder(context)
                        alertDialog.setIcon(R.drawable.avatar_1)
                        alertDialog.setTitle("Single User id: ${response.body()?.id}")
                        alertDialog.setMessage("Single Album name: ${response.body()?.title}")

                        alertDialog.setPositiveButton("Nice") { dialog, _ ->
                            dialog.dismiss()
                        }

                        val dialog = alertDialog.create()
                        dialog.show()

                    }
                } catch (e: Exception) {
                    val alertDialog = AlertDialog.Builder(context)
                    alertDialog.setIcon(R.drawable.avatar_1)
                    alertDialog.setTitle("Error")
                    alertDialog.setMessage("Error status: ${e.message}")

                    alertDialog.setPositiveButton("Nice") { dialog, _ ->
                        dialog.dismiss()
                    }

                    val dialog = alertDialog.create()
                    dialog.show()
                }


            }
        }
    }


}
