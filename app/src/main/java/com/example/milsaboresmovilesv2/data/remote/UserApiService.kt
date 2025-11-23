package com.example.milsaboresmovilesv2.data.remote

import com.example.milsaboresmovilesv2.model.LoginRequest
import com.example.milsaboresmovilesv2.model.LoginResponse
import com.example.milsaboresmovilesv2.model.RegisterRequest
import com.example.milsaboresmovilesv2.model.UserDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST


//http://10.0.2.2:8080/api

interface UserApiService {
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @POST("users/register")
    suspend fun register(@Body request: RegisterRequest): UserDto

    @GET("users")
    suspend fun getUsers(
        @Header("Authorization") token: String? = null
    ): List<UserDto>
}