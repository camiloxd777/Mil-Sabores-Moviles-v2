package com.example.milsaboresmovilesv2.data.remote

import com.example.milsaboresmovilesv2.model.RemoteProductDto
import com.example.milsaboresmovilesv2.model.RemoteProductRequest
import retrofit2.http.*

interface ProductApiService {
    @GET("products")
    suspend fun getProducts(): List<RemoteProductDto>

    @GET("products/admin")
    suspend fun getProductsAdmin(@Header("Authorization") token: String): List<RemoteProductDto>

    @POST("products")
    suspend fun addProduct(
        @Header("Authorization") token: String,
        @Body product: RemoteProductRequest
    ): RemoteProductDto

    @PUT("products/{id}")
    suspend fun updateProduct(
        @Header("Authorization") token: String,
        @Path("id") id: Long,
        @Body product: RemoteProductRequest
    ): RemoteProductDto

    @DELETE("products/{id}")
    suspend fun deleteProduct(
        @Header("Authorization") token: String,
        @Path("id") id: Long
    )
}
