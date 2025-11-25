package com.example.milsaboresmovilesv2.model

// Lo que devuelve el backend en /api/users
data class RemoteUserDto(
    val id: Long,
    val email: String,
    val nombre: String,
    val username: String,
    val fechaNacimiento: String?,
    val rol: String,
)

// Lo que se envía en el PUT /api/users/{id}
data class UpdateUserRequest(
    val nombre: String? = null,
    val username: String? = null,
    val email: String? = null,
    val password: String? = null,
    val rol: String? = null,
)