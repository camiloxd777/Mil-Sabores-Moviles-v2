package com.example.milsaboresmovilesv2.data.repository

import com.example.milsaboresmovilesv2.data.remote.ProductApiService
import com.example.milsaboresmovilesv2.data.remote.RetrofitInstance
import com.example.milsaboresmovilesv2.model.RemoteProductDto
import com.example.milsaboresmovilesv2.model.RemoteProductRequest

class ProductRepository(
    private val api: ProductApiService = RetrofitInstance.productApi
) {
    suspend fun getRemoteProducts(token: String): List<RemoteProductDto> =
        api.getProducts("Bearer $token")

    suspend fun getRemoteProductsAdmin(token: String): List<RemoteProductDto> =
        api.getProductsAdmin("Bearer $token")

    suspend fun addRemoteProduct(token: String, req: RemoteProductRequest): RemoteProductDto =
        api.addProduct("Bearer $token", req)

    suspend fun updateRemoteProduct(token: String, id: Long, req: RemoteProductRequest): RemoteProductDto =
        api.updateProduct("Bearer $token", id, req)

    suspend fun deleteRemoteProduct(token: String, id: Long) =
        api.deleteProduct("Bearer $token", id)
}