package com.example.milsaboresmovilesv2.model

data class RegisterRequest(
    val email: String,
    val nombre: String,
    val username: String,
    val fechaNacimiento: String,
    val password: String,
    val codigoPromo: String?
)