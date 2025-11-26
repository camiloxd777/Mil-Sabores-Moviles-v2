package com.example.milsaboresmovilesv2.data.remote

import com.example.milsaboresmovilesv2.model.RemoteProductDto
import com.example.milsaboresmovilesv2.model.RemoteProductRequest
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

// BASE_URL http://10.0.2.2:8080/api/

interface ProductApiService {

    @GET("products")
    suspend fun getProducts(
        @Header("Authorization") token: String
    ): List<RemoteProductDto>

    @GET("products/admin")
    suspend fun getProductsAdmin(
        @Header("Authorization") token: String
    ): List<RemoteProductDto>

    @POST("products")
    suspend fun addProduct(
        @Header("Authorization") token: String,
        @Body request: RemoteProductRequest
    ): RemoteProductDto

    @PUT("products/{id}")
    suspend fun updateProduct(
        @Header("Authorization") token: String,
        @Path("id") id: Long,
        @Body request: RemoteProductRequest
    ): RemoteProductDto

    @DELETE("products/{id}")
    suspend fun deleteProduct(
        @Header("Authorization") token: String,
        @Path("id") id: Long
    )
}