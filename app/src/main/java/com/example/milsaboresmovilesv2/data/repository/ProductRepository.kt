package com.example.milsaboresmovilesv2.data.repository

import com.example.milsaboresmovilesv2.data.remote.ProductApiService
import com.example.milsaboresmovilesv2.data.remote.RetrofitInstance
import com.example.milsaboresmovilesv2.model.RemoteProductDto
import com.example.milsaboresmovilesv2.model.RemoteProductRequest

class ProductRepository(
    private val api: ProductApiService = RetrofitInstance.productApi
) {
    private fun getAuthHeader(token: String) = if (token.startsWith("Bearer ")) token else "Bearer $token"

    suspend fun getRemoteProducts(): List<RemoteProductDto> =
        api.getProducts()

    suspend fun getRemoteProductsAdmin(token: String): List<RemoteProductDto> =
        api.getProductsAdmin(getAuthHeader(token))

    suspend fun addRemoteProduct(token: String, req: RemoteProductRequest): RemoteProductDto =
        api.addProduct(getAuthHeader(token), req)

    suspend fun updateRemoteProduct(token: String, id: Long, req: RemoteProductRequest): RemoteProductDto =
        api.updateProduct(getAuthHeader(token), id, req)

    suspend fun deleteRemoteProduct(token: String, id: Long) =
        api.deleteProduct(getAuthHeader(token), id)
}
