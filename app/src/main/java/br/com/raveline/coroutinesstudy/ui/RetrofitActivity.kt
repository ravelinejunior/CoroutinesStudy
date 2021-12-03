package br.com.raveline.coroutinesstudy.ui

import android.os.Bundle
import android.util.Log
import android.view.animation.OvershootInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import br.com.raveline.coroutinesstudy.data.remote.model.Albums
import br.com.raveline.coroutinesstudy.data.remote.request.AlbumService
import br.com.raveline.coroutinesstudy.data.remote.request.RetrofitInstance
import br.com.raveline.coroutinesstudy.databinding.ActivityRetrofitBinding
import br.com.raveline.coroutinesstudy.ui.adapter.RetrofitModelAdapter
import jp.wasabeef.recyclerview.animators.SlideInRightAnimator
import retrofit2.Response

class RetrofitActivity : AppCompatActivity() {

    private lateinit var retroBinding: ActivityRetrofitBinding
    private var retrofitModelAdapter :RetrofitModelAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retroBinding = ActivityRetrofitBinding.inflate(layoutInflater)
        setContentView(retroBinding.root)

        retrofitModelAdapter = RetrofitModelAdapter(this)

        val retrofitService =
            RetrofitInstance.getRetrofitInstance().create(AlbumService::class.java)

        val responseLiveData: LiveData<Response<Albums>> = liveData {
            val response = retrofitService.getAlbums()
            emit(response)
        }

        responseLiveData.observe(this, { response ->

            if(response.isSuccessful){
                if(response.body() != null ){
                    retrofitModelAdapter?.setAlbumsItemData(response.body()!!)
                }
            }
            val albumsList = response.body()?.listIterator()
            if (albumsList != null) {
                while (albumsList.hasNext()) {
                    val albums = albumsList.next()
                    Log.i("RetrofitActivity", "Album: ${albums.title}\n")
                }
            }
        })

        setupRecyclerView()

    }
    private fun setupRecyclerView(){
        retroBinding.recyclerViewRetrofitActivity.apply {
            setHasFixedSize(true)
            setHasTransientState(true)
            itemAnimator = SlideInRightAnimator(OvershootInterpolator(5f))
            adapter = retrofitModelAdapter
        }
    }

}