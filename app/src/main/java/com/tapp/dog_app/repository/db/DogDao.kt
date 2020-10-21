package com.tapp.dog_app.repository.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.tapp.dog_app.repository.model.DogResponse

// Para guardar la imagen
@Dao
abstract class DogDao {

    @Query("SELECT * FROM dog_table")
    abstract fun getAllDog(): LiveData<List<DogResponse>>

    @Query("SELECT * FROM dog_table")
    abstract fun getAll(): List<DogResponse>

    @Query("SELECT * FROM dog_table WHERE id = :dogId")
    abstract fun getDogId(dogId: String) : LiveData<DogResponse>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertDog(dogResponse: DogResponse)

    @Delete
    abstract fun deleteDog(dogResponse: DogResponse)
}