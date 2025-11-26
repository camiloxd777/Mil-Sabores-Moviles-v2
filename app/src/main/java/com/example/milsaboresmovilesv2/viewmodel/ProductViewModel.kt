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

    // Fixed: Added 'token' parameter because Repository requires it
    fun loadProducts(token: String = "") {
        viewModelScope.launch {
            try {
                // Fixed: Passed token to repository
                _products.value = repository.getRemoteProducts(token)
            } catch (e: Exception) {
                _error.value = "Error cargando productos: ${e.message}"
            }
        }
    }

    suspend fun loadAdminProducts(token: String) {
        _adminProducts.value = repository.getRemoteProductsAdmin(token)
    }

    suspend fun deleteProduct(token: String, id: Long) {
        repository.deleteRemoteProduct(token, id)
        loadAdminProducts(token)
    }

    // Fixed: Added 'token' parameter
    fun toggleProductActivo(token: String, id: Long, nuevoActivo: Boolean) {
        viewModelScope.launch {
            try {
                val product = _adminProducts.value.firstOrNull { it.id == id } ?: return@launch
                val req = RemoteProductRequest(
                    nombre = product.nombre,
                    descripcion = product.descripcion,
                    precio = product.precio,
                    categoria = product.categoria,
                    activo = nuevoActivo
                )
                // Fixed: Passed token as first argument
                val updated = repository.updateRemoteProduct(token, id, req)

                _adminProducts.value = _adminProducts.value.map {
                    if (it.id == id) updated else it
                }
            } catch (e: Exception) {
                _error.value = "Error cambiando estado: ${e.message}"
            }
        }
    }

    // Fixed: Added 'token' parameter
    fun addProduct(token: String, req: RemoteProductRequest) {
        viewModelScope.launch {
            try {
                // Fixed: Passed token as first argument
                val created = repository.addRemoteProduct(token, req)
                _adminProducts.value = _adminProducts.value + created
            } catch (e: Exception) {
                _error.value = "Error agregando producto: ${e.message}"
            }
        }
    }
}
