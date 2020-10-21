package com.tapp.dog_app.repository.network

import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class DogService {

    interface CallbackResponse<T> {
        fun onResponse(response: T)
        fun onFailure(t: Throwable, res: Response<*>? = null)
    }

    val dogApi: DogApi

    init {

        val timeout: Long = 6 * 1000

        val client = OkHttpClient.Builder()
            .connectTimeout(timeout, TimeUnit.MILLISECONDS)
            .writeTimeout(timeout, TimeUnit.MILLISECONDS)
            .readTimeout(timeout, TimeUnit.MILLISECONDS)
            .build()

        val retrofit = Retrofit.Builder()
            .client(client)
            .addConverterFactory(GsonConverterFactory.create()) // Aquí parsea el objeto
            .baseUrl("https://dog.ceo")
            .build()

        dogApi = retrofit.create(DogApi::class.java)
    }
}