package com.example.milsaboresmovilesv2

import com.example.milsaboresmovilesv2.data.remote.ProductApiService
import com.example.milsaboresmovilesv2.data.repository.ProductRepository
import com.example.milsaboresmovilesv2.model.RemoteProductDto
import com.example.milsaboresmovilesv2.model.RemoteProductRequest
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ProductRepositoryTest {

    private val api: ProductApiService = mockk()
    private val repository = ProductRepository(api)
    private val dummyToken = "test-token"

    @Test
    fun `getRemoteProducts returns list of products`() = runTest {
        val expectedList = listOf(
            RemoteProductDto(1, "Producto 1", "Descripción 1", 1000, "Categoria 1",true),
            RemoteProductDto(2, "Producto 2", "Descripción 2", 2000, "Categoria 2",true)
        )

        coEvery { api.getProducts() } returns expectedList

        val result = repository.getRemoteProducts()

        assertEquals(expectedList, result)
    }

    @Test
    fun `addRemoteProduct returns created product`() = runTest {
        val request = RemoteProductRequest("Nuevo", "Descripción nueva", 1500, "Categoria 1",true)
        val expected = RemoteProductDto(100, "Nuevo", "Descripción nueva", 1500, "Categoria 1",true)

        coEvery { api.addProduct("Bearer $dummyToken", request) } returns expected

        val result = repository.addRemoteProduct(dummyToken, request)

        assertEquals(expected, result)
    }

    @Test
    fun `updateRemoteProduct returns updated product`() = runTest {
        val request = RemoteProductRequest("Actualizado", "Descripción actualizada", 2500, "Categoria 1",true)
        val expected = RemoteProductDto(55, "Actualizado", "Descripción actualizada", 2500,"Categoria 1", true)
        val productId = 55L

        coEvery { api.updateProduct("Bearer $dummyToken", productId, request) } returns expected

        val result = repository.updateRemoteProduct(dummyToken, productId, request)

        assertEquals(expected, result)
    }
}
