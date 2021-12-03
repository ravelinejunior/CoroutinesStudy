package br.com.raveline.coroutinesstudy.data.remote.request

import br.com.raveline.coroutinesstudy.data.remote.model.Albums
import br.com.raveline.coroutinesstudy.data.remote.model.AlbumsItem
import retrofit2.Response
import retrofit2.http.*

interface AlbumService {
    @GET("albums")
    suspend fun getAlbums(): Response<Albums>

    @GET("albums")
    suspend fun getSortedAlbums(@Query("userId") userId: Int): Response<Albums>

    @GET("albums/{id}")
    suspend fun getAlbum(@Path(value = "id") albumId: Int): Response<AlbumsItem>

    @POST("albums")
    suspend fun postAlbum(@Body album: AlbumsItem): Response<AlbumsItem>
}