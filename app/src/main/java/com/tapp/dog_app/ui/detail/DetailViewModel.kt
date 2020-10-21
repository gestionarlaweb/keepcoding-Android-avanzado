package com.tapp.dog_app.ui.detail

import android.app.Activity
import android.app.Application
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.tapp.dog_app.R
import com.tapp.dog_app.repository.db.DogRoomDatabase
import com.tapp.dog_app.repository.model.DogResponse
import com.tapp.dog_app.repository.network.DogService
import com.tapp.dog_app.utils.ApiKey
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(private val context: Application) : ViewModel() {

    fun getApod(cb: DogService.CallbackResponse<DogResponse>) {
        DogService().dogApi.getDog(ApiKey.API_KEY).enqueue(object : Callback<DogResponse> {
            override fun onResponse(call: Call<DogResponse>, response: Response<DogResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    cb.onResponse(response.body()!!)
                } else {
                    cb.onFailure(Throwable(response.message()), response)
                }
            }

            override fun onFailure(call: Call<DogResponse>, t: Throwable) {
                cb.onFailure(t)
            }
        })
    }

// Inserta la imagen en la BBDD
    fun insertDog(dogResponse: DogResponse) {
        DogRoomDatabase.getInstance(context).dogDao().insertDog(dogResponse)
    }

    fun getLocalDogId(apodId: String) : LiveData<DogResponse>{
        return DogRoomDatabase.getInstance(context).dogDao().getDogId(apodId)
    }

    fun deleteDog(dogResponse: DogResponse) {
        DogRoomDatabase.getInstance(context).dogDao().deleteDog(dogResponse)
    }

//
    fun showDog(context: Activity, txtDescription: TextView, imageApodDetail: ImageView, dogResponse: DogResponse) {

        txtDescription.text = dogResponse.status


        Glide.with(context)
            .load(dogResponse.message)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.ic_baseline_sentiment_very_dissatisfied)

            )
            .into(imageApodDetail)
    }
}