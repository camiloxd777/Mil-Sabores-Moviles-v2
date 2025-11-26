package com.example.milsaboresmovilesv2.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.milsaboresmovilesv2.data.repository.ProductRepository
import com.example.milsaboresmovilesv2.model.RemoteProductDto
import com.example.milsaboresmovilesv2.model.RemoteProductRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductViewModel(
    private val repository: ProductRepository = ProductRepository()
) : ViewModel() {

    private val _products = MutableStateFlow<List<RemoteProductDto>>(emptyList())
    val products: StateFlow<List<RemoteProductDto>> = _products

    private val _adminProducts = MutableStateFlow<List<RemoteProductDto>>(emptyList())
    val adminProducts: StateFlow<List<RemoteProductDto>> = _adminProducts

    private val _error = MutableStateFlow("")
    val error: StateFlow<String> = _error

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun loadProducts() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _products.value = repository.getRemoteProducts()
                _error.value = ""
            } catch (e: Exception) {
                _error.value = "Error cargando productos: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun loadAdminProducts(token: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _adminProducts.value = repository.getRemoteProductsAdmin(token)
                _error.value = ""
            } catch (e: Exception) {
                _error.value = "Error cargando productos admin: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    suspend fun deleteProduct(token: String, id: Long) {
        try {
            repository.deleteRemoteProduct(token, id)
            _adminProducts.value = _adminProducts.value.filterNot { it.id == id }
        } catch (e: Exception) {
            _error.value = "Error eliminando producto: ${e.message}"
            throw e
        }
    }

    suspend fun toggleProductActivo(token: String, id: Long, nuevoActivo: Boolean) {
        try {
            val product = _adminProducts.value.firstOrNull { it.id == id } ?: return
            val req = RemoteProductRequest(
                nombre = product.nombre,
                descripcion = product.descripcion,
                precio = product.precio,
                categoria = product.categoria,
                activo = nuevoActivo
            )
            val updated = repository.updateRemoteProduct(token, id, req)
            _adminProducts.value = _adminProducts.value.map {
                if (it.id == id) updated else it
            }
        } catch (e: Exception) {
            _error.value = "Error cambiando estado: ${e.message}"
            throw e
        }
    }

    fun addProduct(token: String, req: RemoteProductRequest) {
        viewModelScope.launch {
            try {
                val created = repository.addRemoteProduct(token, req)
                _adminProducts.value = _adminProducts.value + created
            } catch (e: Exception) {
                _error.value = "Error agregando producto: ${e.message}"
            }
        }
    }
}
