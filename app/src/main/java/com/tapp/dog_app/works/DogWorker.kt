package com.tapp.dog_app.works

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.tapp.dog_app.repository.db.DogRoomDatabase
import com.tapp.dog_app.repository.model.DogResponse
import com.tapp.dog_app.repository.network.DogService
import com.tapp.dog_app.utils.ApiKey
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class DogWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    override fun doWork(): Result {

        DogService().dogApi.getDog(ApiKey.API_KEY).enqueue(object : Callback<DogResponse> {
            override fun onResponse(call: Call<DogResponse>, response: Response<DogResponse>) {
                if (response.isSuccessful && response.body() != null) {

                    val dogResponse = response.body()

                    try {
                        DogRoomDatabase.getInstance(applicationContext).dogDao().getAll().first {
                            it.message == dogResponse!!.message // url
                        }
                    } catch (e: Exception) {
                        DogRoomDatabase.getInstance(applicationContext).dogDao().insertDog(dogResponse!!)
                    }
                }
            }

            override fun onFailure(call: Call<DogResponse>, t: Throwable) {
                Log.w("TAG", t.localizedMessage!!)
            }
        })

        return Result.success()
    }

}