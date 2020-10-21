package com.tapp.dog_app.ui.main

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.tapp.dog_app.repository.db.DogRoomDatabase
import com.tapp.dog_app.repository.model.DogResponse

class MainFragmentViewModel(private val context: Application) : ViewModel() {

    fun gelLocalAllDog() : LiveData<List<DogResponse>> {
         return DogRoomDatabase.getInstance(context).dogDao().getAllDog()
    }
}