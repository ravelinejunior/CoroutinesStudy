package br.com.raveline.coroutinesstudy.data.remote.request

import br.com.raveline.coroutinesstudy.utils.Constants.BASE_URL
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitInstance {
    companion object {

        private val interceptor = HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }

        private val client = OkHttpClient.Builder()
            .connectTimeout(15000, TimeUnit.MILLISECONDS)
            .callTimeout(15000L, TimeUnit.MILLISECONDS)
            .writeTimeout(15000L, TimeUnit.MILLISECONDS)
            .addInterceptor(interceptor)
            .build()

        fun getRetrofitInstance(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .client(client)
                .build()
        }

    }
}