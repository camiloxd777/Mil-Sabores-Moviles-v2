package com.example.milsaboresmovilesv2.data.repository

import com.example.milsaboresmovilesv2.data.remote.ProductApiService
import com.example.milsaboresmovilesv2.data.remote.RetrofitInstance
import com.example.milsaboresmovilesv2.model.RemoteProductDto
import com.example.milsaboresmovilesv2.model.RemoteProductRequest

class ProductRepository(
    private val api: ProductApiService = RetrofitInstance.productApi
) {
    suspend fun getRemoteProducts(): List<RemoteProductDto> =
        api.getProducts()

    suspend fun getRemoteProductsAdmin(): List<RemoteProductDto> =
        api.getProductsAdmin()

    suspend fun addRemoteProduct(req: RemoteProductRequest): RemoteProductDto =
        api.addProduct(req)

    suspend fun updateRemoteProduct(id: Long, req: RemoteProductRequest): RemoteProductDto =
        api.updateProduct(id, req)

    suspend fun deleteRemoteProduct(id: Long) =
        api.deleteProduct(id)
}