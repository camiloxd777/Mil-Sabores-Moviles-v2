package com.example.milsaboresmovilesv2.model

data class UserDto(
    val id: Long?,
    val email: String,
    val nombre: String,
    val username: String,
    val fechaNacimiento: String,
    val codigoPromo: String?,
    val rol: String
)