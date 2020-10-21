package com.tapp.dog_app.repository.network

import com.tapp.dog_app.repository.model.DogResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface DogApi {

    @GET("api/breeds/image/random") //  planetary/apod
    @Headers("Content-Type: application/json")
    // Para la apiKey
    fun getDog(@Query("") apiKey: String): Call<DogResponse> // api_key


}