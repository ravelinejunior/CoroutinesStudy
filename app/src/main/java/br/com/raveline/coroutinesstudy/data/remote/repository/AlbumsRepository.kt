package br.com.raveline.coroutinesstudy.data.remote.repository

import br.com.raveline.coroutinesstudy.data.remote.model.Albums
import retrofit2.Response

sealed interface AlbumsRepository {
    suspend fun getAlbums(): Response<Albums>
}