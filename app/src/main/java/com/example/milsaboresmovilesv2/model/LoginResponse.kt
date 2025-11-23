package com.example.milsaboresmovilesv2.model

data class LoginResponse(
    val token: String,
    val user: UserDto
)