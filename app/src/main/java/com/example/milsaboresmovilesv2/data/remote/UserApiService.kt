package com.example.milsaboresmovilesv2.data.remote

import com.example.milsaboresmovilesv2.model.LoginRequest
import com.example.milsaboresmovilesv2.model.LoginResponse
import com.example.milsaboresmovilesv2.model.RegisterRequest
import com.example.milsaboresmovilesv2.model.UserDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path


//http://10.0.2.2:8080/api


data class RemoteUserDto(
    val id: Long,
    val nombre: String,
    val email: String,
    val username: String,
    val rol: String
)

data class UpdateUserRequest(
    val nombre: String? = null,
    val username: String? = null,
    val email: String? = null,
    val password: String? = null,
    val rol: String? = null
)


interface UserApiService {

    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @POST("users/register")
    suspend fun register(@Body request: RegisterRequest): UserDto

    // LISTAR USUARIOS (panel admin)
    @GET("admin/users")
    suspend fun getUsers(
        @Header("Authorization") authHeader: String
    ): List<RemoteUserDto>

    // ACTUALIZAR USUARIO (panel admin)
    @PUT("admin/users/{id}")
    suspend fun updateUser(
        @Header("Authorization") authHeader: String,
        @Path("id") id: Long,
        @Body request: UpdateUserRequest
    ): RemoteUserDto

    // ELIMINAR USUARIO (panel admin)
    @DELETE("admin/users/{id}")
    suspend fun deleteUser(
        @Header("Authorization") authHeader: String,
        @Path("id") id: Long
    )
}