package com.tapp.dog_app.repository.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*

// Este Entity es para transfromar en una tabla cuando tengammos que gardar datos en la tabla de Room
@Entity(tableName = "dog_table")
data class DogResponse(

	// Este PrimaryKey es para la tabla de Room
	@PrimaryKey
	var id: String = UUID.randomUUID().toString(),

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null

) : Serializable