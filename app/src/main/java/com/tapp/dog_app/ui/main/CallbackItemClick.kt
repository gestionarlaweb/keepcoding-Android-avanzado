package com.tapp.dog_app.ui.main

import com.tapp.dog_app.repository.model.DogResponse

interface CallbackItemClick {
    fun onItemClick(dogResponse: DogResponse)
}