package com.example.milsaboresmovilesv2

import com.example.milsaboresmovilesv2.data.repository.ProductRepository
import com.example.milsaboresmovilesv2.model.RemoteProductDto
import com.example.milsaboresmovilesv2.model.RemoteProductRequest
import com.example.milsaboresmovilesv2.viewmodel.ProductViewModel
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain

@OptIn(ExperimentalCoroutinesApi::class)
class ProductViewModelTest : StringSpec({

    lateinit var mockRepo: ProductRepository
    val testDispatcher = StandardTestDispatcher()

    beforeTest {
        Dispatchers.setMain(testDispatcher)
        mockRepo = mockk()
    }

    afterTest {
        Dispatchers.resetMain()
    }

    "loadProducts debe cargar productos correctamente" {
        runTest {
            val mockProducts = listOf(
                RemoteProductDto(1, "Pan", "Pan fresco", 1000, "Panadería", true),
                RemoteProductDto(2, "Jugo", "Naranja", 1500, "Bebidas", true)
            )

            coEvery { mockRepo.getRemoteProducts() } returns mockProducts

            val viewModel = ProductViewModel(mockRepo)

            viewModel.loadProducts()
            advanceUntilIdle()

            viewModel.products.value shouldBe mockProducts
        }
    }

    "loadAdminProducts debe cargar productos admin" {
        runTest {
            val mockAdmins = listOf(
                RemoteProductDto(10, "Pizza", "Grande", 8990, "Comida", true)
            )

            coEvery { mockRepo.getRemoteProductsAdmin() } returns mockAdmins

            val viewModel = ProductViewModel(mockRepo)

            viewModel.loadAdminProducts()
            advanceUntilIdle()

            viewModel.adminProducts.value shouldBe mockAdmins
        }
    }

    "toggleProductActivo debe actualizar el producto correctamente" {
        runTest {
            val original = RemoteProductDto(1, "Pan", "Fresco", 1000, "Panadería", true)
            val updated = original.copy(activo = false)
            val request = RemoteProductRequest("Pan", "Fresco", 1000, "Panadería", false)


            coEvery { mockRepo.getRemoteProductsAdmin() } returns listOf(original)
            coEvery { mockRepo.updateRemoteProduct(1, request) } returns updated

            val viewModel = ProductViewModel(mockRepo)

            viewModel.loadAdminProducts()
            advanceUntilIdle()

            viewModel.toggleProductActivo(1, false)
            advanceUntilIdle()

            viewModel.adminProducts.value.first().activo shouldBe false
        }
    }

    "addProduct debe agregar un nuevo producto al state" {
        runTest {
            val request = RemoteProductRequest(
                nombre = "Torta",
                descripcion = "Chocolate",
                precio = 12000,
                categoria = "Pastelería",
                activo = true
            )

            val created = RemoteProductDto(99, "Torta", "Chocolate", 12000, "Pastelería", true)

            coEvery { mockRepo.addRemoteProduct(request) } returns created
            coEvery { mockRepo.getRemoteProductsAdmin() } returns emptyList()


            val viewModel = ProductViewModel(mockRepo)
            viewModel.loadAdminProducts()
            advanceUntilIdle()

            viewModel.addProduct(request)
            advanceUntilIdle()

            viewModel.adminProducts.value shouldBe listOf(created)
        }
    }

    "loadProducts debe guardar error cuando falla" {
        runTest {
            coEvery { mockRepo.getRemoteProducts() } throws RuntimeException("Fallo conexión")

            val viewModel = ProductViewModel(mockRepo)

            viewModel.loadProducts()
            advanceUntilIdle()

            viewModel.error.value shouldBe "Error cargando productos: Fallo conexión"
        }
    }
})