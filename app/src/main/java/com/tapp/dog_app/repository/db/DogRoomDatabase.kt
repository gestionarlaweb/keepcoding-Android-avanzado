package com.tapp.dog_app.repository.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.tapp.dog_app.repository.model.DogResponse

@Database(entities = [DogResponse::class], version = 1, exportSchema = false)
abstract class DogRoomDatabase : RoomDatabase() {

    abstract fun dogDao(): DogDao

    companion object {

        private var instance: DogRoomDatabase? = null

        fun getInstance(context: Context): DogRoomDatabase {

            if (instance == null) {

                synchronized(DogRoomDatabase::class) {
                    instance = Room.databaseBuilder(context.applicationContext, DogRoomDatabase::class.java, "dog_db") // Nombre BBDD  apod_db
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }

            return instance!!
        }
    }
}