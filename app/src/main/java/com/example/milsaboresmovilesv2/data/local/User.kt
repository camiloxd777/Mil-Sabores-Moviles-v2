package com.example.milsaboresmovilesv2.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val email: String,
    val nombre: String,
    val username: String,
    val fechaNacimiento: String,
    val password: String,
    val codigoPromo: String? = null
)